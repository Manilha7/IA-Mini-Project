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
			double tmp = child.totalScore / child.numberOfVisits;
			//System.out.println(tmp);
            if(tmp > winRatio){
                result =child;
                winRatio = tmp;
            }
        }
        return result;
    }

	public Ilayout solve(Ilayout s) {


		State root = new State(s, null);
		State promisingNode;
		int i=0;
		root.layout.setPlayer(-1);
		while (i<1000) {
		
			promisingNode = selectPromisingState(root);
			//System.out.println((Board) promisingNode.layout);
			expansion(promisingNode);
			State ts = simulation(promisingNode);
			//System.out.println(winscore);
			backpropagation(promisingNode, ts);
			i++;
		}
		State winstate= bestChild(root.childArray);
		//System.out.println((Board) winstate.layout);
		return winstate.layout ;
	}

	public static State selectPromisingState(State rootState) {
		State state = rootState;
		while (state.childArray.size() > 0) {
			state = findBestNodeWithUCT(state.childArray);
		}
		return state;

	}
	
	public static State findBestNodeWithUCT(List<State> childs){
        State result = null;
        double uctPrevious = -1;
        for (State child: childs) {
			double uct= uctValue(child);
            if(uct > uctPrevious){
                result = child;
                uctPrevious = uctValue(child);
            }
        }
        return result;
    }


	public static double uctValue(State child) {
		double parentVisit = child.father.numberOfVisits;
		double nodeVisits = child.numberOfVisits;
		if (nodeVisits == 0) {
			return Double.MAX_VALUE;
		}
		return ((child.totalScore / nodeVisits) + 1.41* Math.sqrt(Math.log(parentVisit) / nodeVisits));
	}

	


	
	private static State simulation(State child) {
		double winscore = child.layout.getResult();
		//System.out.println(winscore);
			Random rand = new Random();
			List<Ilayout> temporarychilds = child.layout.children();
			State ts = child;
			while (temporarychilds.size() != 0 && winscore == 0.5) {
				ts = new State(temporarychilds.get(rand.nextInt(temporarychilds.size())), ts);
				winscore = ts.layout.getResult();
				//System.out.println((Board) ts.layout);
				//System.out.println(winscore);
				//System.out.println("top: "+ts.layout.getPlayer());
				temporarychilds = ts.layout.children();
		}
		//System.out.println((Board) ts.layout);
		//System.out.println(winscore);
		return ts;
	}

	private static void backpropagation(State promisingNode, State ts) {
		double winscore = ts.layout.getResult();
		promisingNode.numberOfVisits++;
		if (promisingNode.layout.getPlayer()==1.0 && winscore==1.0) {
			promisingNode.totalScore += winscore;
		}
		if (promisingNode.layout.getPlayer()==-1.0 && winscore==0.0) {
			promisingNode.totalScore ++;
		}
		if (winscore==0.5) {
			promisingNode.totalScore+=winscore;
		}

		while (promisingNode.father != null) {
			promisingNode = promisingNode.father;
			promisingNode.numberOfVisits++;
			if (promisingNode.layout.getPlayer()==1.0 && winscore==1.0) {
				promisingNode.totalScore += winscore;
			}
			if (promisingNode.layout.getPlayer()==-1.0 && winscore==0.0) {
				promisingNode.totalScore ++;
			}
			if (winscore==0.5) {
				promisingNode.totalScore+=winscore;
			}
	
		}
	}




	public static void expansion(State promState) {
		//System.out.println("top: "+ promState.layout.getPlayer());
		List<State> sucs = sucessors(promState);
		promState.setChildArray(sucs);
	}
}