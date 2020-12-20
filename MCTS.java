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



	public static Board solve(Ilayout s) {


		State promisingNode = new State(s, null);

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (60 * 60 * 1000);// 1 hour

		//while (startTime < endTime) {
		
			promisingNode = selectPromisingState(promisingNode);
			expansion(promisingNode);
			for (State child : promisingNode.childArray) {
				double winscore = simulation(child);
				System.exit(0);
				backpropagation(child, winscore);
			}
		//}

		return (Board) promisingNode.layout ;
	}

	public static State selectPromisingState(State rootState) {
		State state = rootState;
		while (state.getChildArray().size() != 0) {
			state = findBestNodeWithUCT(state);
		}
		return state;

	}

	private static double simulation(State child) {
		double winscore = 0.5;
			Random rand = new Random();
			List<Ilayout> temporarychilds = child.layout.children();
			State ts = child;
			while (temporarychilds.size() != 0 && winscore == 0.5) {
				ts = new State(temporarychilds.get(rand.nextInt(temporarychilds.size())), ts);
				winscore = ts.layout.getResult();
				System.out.println((Board) ts.layout);
				temporarychilds = ts.layout.children();
		}
		System.out.println(winscore);
		return winscore;
	}

	private static State backpropagation(State promisingNode, double winscore) {
		promisingNode.numberOfVisits++;
		promisingNode.totalScore += winscore;
		while( promisingNode.father !=null){
			promisingNode=promisingNode.father;
			promisingNode.numberOfVisits++;
			promisingNode.totalScore += winscore;
		}
		return promisingNode;

	}


	public static double uctValue(State child) {
		double parentVisit = child.father.numberOfVisits;
		double nodeVisits = child.numberOfVisits;
		if (nodeVisits == 0) {
			return Double.MAX_VALUE;
		}
		return (child.totalScore / nodeVisits) + 1.41 * Math.sqrt(Math.sqrt(parentVisit) / nodeVisits);
	}

	public static State findBestNodeWithUCT(State state) {
		double uctprevious = 0;
		State result = new State();
		for (State child : state.childArray) {
			double uct = uctValue(child);
			if (state.layout.getPlayer() == -1) {
				if (uct > uctprevious) {
					uctprevious = uct;
					result = child;
				}
			} else {
				if (uct < uctprevious) {
					uctprevious = uct;
					result = child;
				}
			}
		}
		return result;

	}

	public static void expansion(State promState) {
		List<State> sucs = sucessors(promState);
		promState.setChildArray(sucs);
	}
}