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
		
		
		/**
		 * Construtor de um State a partir de um Ilayout e um State
		 * @param Ilayout l - Representa a board atual 
		 * @param State father- Representa o State pai do State atual
		 */
		public State(Ilayout l, State father) {
			this.father=father;
			this.layout = l;
			this.childArray = new ArrayList<>();
		}

		/** 
		 * Construtor de um State a partir de um Ilayout e um State
		 * @param l  Ilayout que representa a board atual 
		 * @param father State que representa o State pai do State atual
		 * @param childArray - Lista de State que representa os filhos do State atual
		 */
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

		public State getFather() {
			return this.father;
		}

		public Ilayout getLayout() {
			return this.layout;
		}
	}
	
	/**
	 * Calcula os Sucessores do State n
	 * @param n State no qual se pretende obter os sucessores
	 * @return Lista de States dos sucessores de n
	 */
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

	/**
	 * Calcula o melhor State, com base no numero de visitas
	 * @param childArray - Lista de States que representa os filhos da root
	 * @return O State que representa a melhor jogada a se fazer
	 */
	public static State bestChild(List<State> childArray){
        State result = null;
        double Ratio = Double.MIN_VALUE;
        for (State child: childArray) {
			//System.out.println((Board) child.layout);
			double tmp = child.numberOfVisits;
			//System.out.println(tmp);
            if(tmp > Ratio){
                result =child;
                Ratio = tmp;
            }
        }
        return result;
    }

	
	/**
	 * Algoritmo MonteCarloTreeSearch que descobre a melhor jogada a se fazerr
	 * @param s Ilayout que representa a board atual
	 * @param opponent char carater para saber a que pertence a jogada anterior
	 * @return Ilayout que representa a jogada que o MCTS definiu como melhor.
	 */
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
		while (i<10000) {
		
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
		//System.out.println("Winstate:" + winstate.layout);
		//System.out.println("nv: "+ root.numberOfVisits );
		//System.out.println("win: "+ root.totalScore );
		return winstate.layout ;
	}

	
	/**
	 * Escolhe o melhor State com base no UCT
	 * @param rootState State inicial 
	 * @param bot char que representa que bot e a jogar no caso de ser dois bots a jogar
	 * @return State selecionado com base no UCT
	 */
	public static State selectPromisingState(State rootState, char bot) {
		State state = rootState;
		while (state.childArray.size() > 0) {
			if (bot == 'B') {
				if (state.layout.getPlayer()=='P') {
					state = findBestNodeWithUCTMax(state.childArray);
				}
				else{
					state = findBestNodeWithUCTMin(state.childArray);
				}
			}
			else{
				if (state.layout.getPlayer()=='B') {
					state = findBestNodeWithUCTMax(state.childArray);
				}
				else{
					state = findBestNodeWithUCTMin(state.childArray);
				}
			}
		}
		return state;

	}
	
	/**
	 * Calcula o State que tem menor UCT 
	 * @param childs Lista dos States a ser analisadps 
	 * @return State com o menor UCT
	 */
	public static State findBestNodeWithUCTMin(List<State> childs) {
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

	
	/**
	 * Calcula o State que tem maior UCT 
	 * @param childs Lista dos States a ser analisadps 
	 * @return State com o maior UCT
	 */
	public static State findBestNodeWithUCTMax(List<State> childs) {
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

	/**
	 * Calcula o valor do UCT do State
	 * @param child State para o qual vai ser calculado o UCT
	 * @param minOrmax char para saber se nao existir visitas por +infinito ou -inifito
	 * @return double que corresponde ao valor do UCT para aquele State
	 */
	public static double uctValue(State child, char minOrmax) {
		double parentVisit = child.father.numberOfVisits;
		double nodeVisits = child.numberOfVisits;
		double result = 0;
		if (minOrmax == 'm') {
			if (nodeVisits == 0) {
				result = Double.MIN_VALUE;
			}
		} else if (minOrmax == 'M') {
			if (nodeVisits == 0) {
				result = Double.MAX_VALUE;
			}
		}
		result = ((child.totalScore / nodeVisits) + 0.3 * Math.sqrt(Math.log(parentVisit) / nodeVisits));
		return result;
	}

	
	/**
	 * Verifica se existe State terminais
	 * @param childs Lista de States ao qual vai ser verificado se existe States terminais
	 * @return State que representa o State terminal 
	 */
	public static State checkterminal(List<State> childs) {
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

	
	/**
	 * Calcula as simulacoes da lista de filhos
	 * @param childs Lista de States ao qual se pertende submeter as simulacoes
	 * @return Lista de States que representam os States terminais de cada um dos filhos
	 */
	public static List<State> simulation(List<State> childs) {
		List<State> winlist = new ArrayList<>();
		for (State state : childs) {
			char winscore = state.layout.getResult();
			// System.out.println(winscore);
			Random rand = new Random();
			List<State> temporarychilds = sucessors(state);
			State ts = state;
			while (temporarychilds.size() != 0 && winscore == 'E') {
				if (checkterminal(temporarychilds)== null) {
					ts = new State(temporarychilds.get(rand.nextInt(temporarychilds.size())).layout, ts);
					winscore = ts.layout.getResult();
					//System.out.println((Board) ts.layout);
					//System.out.println(winscore);
					//System.out.println("top: "+ts.layout.getPlayer());
					temporarychilds = sucessors(ts);
				}
				else{
					winscore = checkterminal(temporarychilds).layout.getResult();
					ts= checkterminal(temporarychilds);
				}
		}
		winlist.add(ts);
		}
		//System.out.println((Board) ts.layout);
		//System.out.println(winscore);
		return winlist;
	}

	/**
	 * Propagacao das vitorias/derrotas/empates para os States father
	 * @param childs Lista de filhos ao qual foram submetidas as simulacoes
	 * @param values Lista dos States terminais correspondestes a cada filho
	 * @param bot char que representa que bot e a jogar no caso de ser dois bots a jogar
	 */
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