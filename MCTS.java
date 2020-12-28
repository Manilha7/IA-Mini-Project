import java.util.ArrayList;
import java.util.Random;
import java.util.List;

class MCTS {
	static class State {
		List<State> childArray;
		private State father;
		private Ilayout layout;
		private double totalScore = 0;
		private double numberOfVisits = 0;

		public State() {
		}

		public State(Ilayout l, State father) {
			this.father=father;
			this.layout = l;
			this.childArray = new ArrayList<>();
		}

		public State(Ilayout l, State father, List<State> childArray) {
			this.layout = l;
			this.childArray = childArray;
		}

		double getTotalScore() {
			return totalScore;
		}

		public List<State> getChildArray() {
			return childArray;
		}

		public void setChildArray(List<State> child) {
			this.childArray=child;

		}

		/**
		 * Permite dar print do Ilayout do estado
		 * 
		 * @return representacao em String do estado a que se refere
		 */
		public String toString() {
			return layout.toString();
		}
	}

	final private static List<State> sucessors(State n) {
		List<State> sucs = new ArrayList<>();
		List<Ilayout> children = n.layout.children();
		for (Ilayout e : children) {
			if (n.father == null || !e.equals(n.father.layout)) {
				State nn = new State(e, n);
				sucs.add(nn);
			}
		}
		return sucs;
	}

	public static State bestChild(List<State> childArray){
        State result = null;
        double winRatio = Double.MIN_VALUE;
        for (State child: childArray) {
			//System.out.println((Board) child.layout);
			double tmp = child.numberOfVisits;
			//System.out.println(tmp);
            if(tmp > winRatio){
                result =child;
                winRatio = tmp;
            }
        }
        return result;
    }

	public Ilayout solve(Ilayout s, char opponent) {


		State root = new State(s, null);
		State promisingNode;
		int i=0;
		List<State> childs;
		root.layout.setPlayer(opponent);
		char player;
		if (opponent== 'B') {
			player='P';
		}
		else{
			player='B';
		}
		while (i<1000) {
		
			promisingNode = selectPromisingState(root, player);
			//System.out.println((Board) promisingNode.layout);
			childs =expansion(promisingNode);
			//System.out.println(childs);
			List<State> values;
			values= simulation(childs);
			//System.out.println(winscore);
			backpropagation(childs, values, player);
			i++;
		}
		State winstate= bestChild(root.childArray);
		System.out.println("Winstate:" + winstate.layout);
		return winstate.layout ;
	}

	public static State selectPromisingState(State rootState, char bot) {
		State state = rootState;
		while (state.childArray.size() > 0) {
			if (bot == 'B') {
				if (state.layout.getPlayer()=='P') {
					state = findBestNodeWithUCTMax(state.childArray, 'B');
				}
				else{
					state = findBestNodeWithUCTMin(state.childArray, 'P');
				}
			}
			else{
				if (state.layout.getPlayer()=='B') {
					state = findBestNodeWithUCTMax(state.childArray, 'P');
				}
				else{
					state = findBestNodeWithUCTMin(state.childArray, 'B');
				}
			}
		}
		return state;

	}
	
	public static State findBestNodeWithUCTMin(List<State> childs, char bot) {
		State result = null;
		double uctPrevious = Double.MAX_VALUE;
		for (State child : childs) {
			double uct = uctValue(child,'m');
			if (uct < uctPrevious) {
				result = child;
				uctPrevious = uctValue(child,'m');
			}
		}

		return result;
	}

	public static State findBestNodeWithUCTMax(List<State> childs, char bot) {
		State result = null;
		double uctPrevious = -1;
		for (State child : childs) {
			double uct = uctValue(child,'M');
			if (uct > uctPrevious) {
				result = child;
				uctPrevious = uctValue(child,'M');
			}
		}

		return result;
	}

	public static double uctValue(State child, char minOrmax) {
		double parentVisit = child.father.numberOfVisits;
		double nodeVisits = child.numberOfVisits;
		double result=0;
		if (minOrmax== 'm') {
			if (nodeVisits == 0) {
				result= Double.MIN_VALUE;
			}
			result = ((child.totalScore / nodeVisits) + 0.5 * Math.sqrt(Math.log(parentVisit) / nodeVisits));
		}
		else if (minOrmax== 'M') {
			if (nodeVisits == 0) {
				result= Double.MAX_VALUE;
			}
			result= ((child.totalScore / nodeVisits) + 0.5 * Math.sqrt(Math.log(parentVisit) / nodeVisits));
		}
		return result;
	}
	
	public static State checkwin(List<State> childs) {
		State result = null;
		for (State state : childs) {
			if (state.layout.getResult() == 'E') {
				result = null;
			} else {
				return state;
			}

		}
		return result;
	}

	private static List<State> simulation(List<State> childs) {
		List<State> winlist = new ArrayList<>();
		for (State state : childs) {
			char winscore = state.layout.getResult();
			// System.out.println(winscore);
			Random rand = new Random();
			List<State> temporarychilds = sucessors(state);
			State ts = state;
			while (temporarychilds.size() != 0 && winscore == 'E') {
				if (checkwin(temporarychilds)== null) {
					ts = new State(temporarychilds.get(rand.nextInt(temporarychilds.size())).layout, ts);
					winscore = ts.layout.getResult();
					//System.out.println((Board) ts.layout);
					//System.out.println(winscore);
					//System.out.println("top: "+ts.layout.getPlayer());
					temporarychilds = sucessors(ts);
				}
				else{
					winscore = checkwin(temporarychilds).layout.getResult();
					ts= checkwin(temporarychilds);
				}
		}
		winlist.add(ts);
		}
		//System.out.println((Board) ts.layout);
		//System.out.println(winscore);
		return winlist;
	}

	private static void backpropagation(List<State> childs, List<State> values, char bot) {
	
		if (values.size()!= childs.size()) {
			System.out.println("erro");
			System.exit(0);
		}
		for (int i = 0; i < childs.size(); i++) {
			childs.get(i).numberOfVisits++;
			double winscore=0;
			if (bot == 'B') {
				char win = values.get(i).layout.getResult();
				if (win == 'B') {
					winscore = 1.0;
				} else if (win == 'P') {
					winscore = 0.0;
				} else {
					winscore = 0.5;
				}
				childs.get(i).totalScore += winscore;
				State state = childs.get(i);
				while (state.father != null) {
					state = state.father;
					state.numberOfVisits++;
					state.totalScore += winscore;

				}
			}
			else if (bot == 'P') {
				char win = values.get(i).layout.getResult();
				if (win == 'B') {
					winscore = 0.0;
				} else if (win == 'P') {
					winscore = 1.0;
				} else {
					winscore = 0.5;
				}
				childs.get(i).totalScore += winscore;
				State state = childs.get(i);
				while (state.father != null) {
					state = state.father;
					state.numberOfVisits++;
					state.totalScore += winscore;

				}
			}
		}
	}





	public static List<State> expansion(State promState) {
		List<State> sucs = sucessors(promState);
		promState.setChildArray(sucs);
		return sucs;
	}
}