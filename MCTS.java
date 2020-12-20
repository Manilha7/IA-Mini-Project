import java.util.ArrayList;
import java.util.Random; 
import java.util.List;

class MCTS {
	static class State {
		List<State> childArray;
		private Ilayout layout;
		private double totalScore = 0;
		private double numberOfVisits = 0;

		public State() {
		}

		public State(Ilayout l) {
			this.layout = l;
			childArray = new ArrayList<>();
		}

		public State(Ilayout l, List<State> childArray) {
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
			State child = new State(e);
			sucs.add(child);
		}
		return sucs;
	}

	private static State selectPromisingState(State rootState) {
		State state = rootState;
		while (state.getChildArray().size() != 0) {
			state = findBestNodeWithUCT(state);
		}
		return state;
	}

	public static Board solve(Ilayout s) {

		State rootState = new State(s);

		long startTime = System.currentTimeMillis();
		long endTime = startTime + (60 * 60 * 1000);// 1 hour

		while (startTime < endTime) {

			State promisingNode = selectPromisingState(rootState);
			expansion(promisingNode);
			simulation(promisingNode);
			double winscore= simulation(promisingNode);
			backpropagation(promisingNode,winscore);
		}

		return null;
	}

	private static void backpropagation(State promisingNode, double winscore) {
		promisingNode.numberOfVisits++;
		promisingNode.totalScore= winscore;
		
	}

	private static double simulation(State promisingNode) {
		double winscore=0;
		for (State child : promisingNode.childArray) {
			Random rand= new Random();
			List<Ilayout> temporarychilds= child.layout.children();
			State ts= null;
			while (temporarychilds.size()!=0) {
				ts = new State(temporarychilds.get(rand.nextInt(temporarychilds.size())));

				temporarychilds= ts.layout.children();
			}
			winscore= ts.layout.getResult();
		}
		return winscore;
	}

	public static double uctValue(double parentVisit, State child) {
		double nodeVisits = child.numberOfVisits;
		if (nodeVisits == 0) {
			return Double.MAX_VALUE;
		}
		return (child.totalScore / nodeVisits)
				+ 1.41 * Math.sqrt(Math.sqrt(parentVisit) / nodeVisits);
	}

	public static State findBestNodeWithUCT(State state) {
		double parentVisit = state.numberOfVisits;
		double uctprevious = 0;
		State result = new State();
		for (State child : state.childArray) {
			double uct = uctValue(parentVisit, child);
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