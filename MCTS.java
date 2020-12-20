import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class MCTS {
	static class State {
		List<State> childArray;
		private Ilayout layout;
        private int totalScore = 0;
        private int numberOfVisits = 0;
		private State state;


		public State(State state,Ilayout l) {
			this.state = state;
			this.layout=l;
			childArray = new ArrayList<>();
		}

		public State(State state, Ilayout l, List<State> childArray) {
			this.state = state;
			this.layout=l;
			this.childArray = childArray;
		}
		
		public State getState() {
			return state;
		}

		double getTotalScore() {
			return totalScore;
		}

		public int getNumberofVisits() {
			return numberOfVisits;
		}

		public List<State> getChildArray() {
			return childArray;
		}

		/**
		 * Permite dar print do Ilayout do estado
		 * @return representacao em String do estado a que se refere
		 */
		public String toString() {
			return layout.toString();
		}

		private static State selectPromisingState(State rootState) {
			State state = rootState;
			while (state.getChildArray().size() != 0) {
				state = findBestNodeWithUCT(state);
			}
			return state;
		}

		public static Board solve(Ilayout s){

			State rootState = new State(null,s);
			
			long startTime = System.currentTimeMillis();
			long endTime = startTime+(60 * 60 * 1000);// 1 hour

			while(startTime < endTime){

				State promisingNode = selectPromisingState(rootState);





			}

			return null;
		}


		public static double uctValue(int totalVisits, double nodeScore, int nodeVisits) {
			if (nodeVisits == 0) {
				return Double.MAX_VALUE;
			}
			return (nodeScore / (double) nodeVisits) + 1.41 * Math.sqrt(Math.log(totalVisits) / (double) nodeVisits);
		}

		static State findBestNodeWithUCT(State state) {
			int parentVisit = state.state.numberOfVisits;
			if(state.layout.getPlayer()== 1) {
				foreach(State child: state.childArray){
					
				}
			}
		}
		
	}
}