import java.util.Scanner;


/**
 * Main de Bot vs Bot
 */
public class Main {

	public static void main(String[] args) {

		int i = 0;
		int emp = 0;
		int first = 0;
		int second = 0;
		//int pf=0;
		while (i < 100) {
			Board board = new Board();

			char opponent = 'P';
			MCTS a = new MCTS();

			while (board.getResult() == 'E' && !board.endofGame()) {
				board = (Board) a.solve(board, opponent);
				//System.out.println(board);
				if (opponent == 'B') {
					opponent = 'P';
				} else if (opponent == 'P') {
					opponent = 'B';
				}

			}


			if (board.getResult() == 'E')
				emp++;
			else if (board.getResult() == 'B')
				first++;
			else if (board.getResult() == 'P')
				second++;
			i++;
			System.out.println(board);
		}
		System.out.println("Empate: " + emp);
		System.out.println("First: " + first);
		System.out.println("Second: " + second);
		//System.out.println("pf: " + pf);
	}
}

/**
 * Main do Bot vs Player
 */

// public class Main {

// 	public static void main(String[] args) {
// 		Board board = new Board();
// 		Scanner sc = new Scanner(System.in);
// 		while (board.getResult() == 'E' && !board.endofGame()) {			
// 			MCTS a = new MCTS();
// 			char player = 'P';
// 			board = (Board) a.solve(board, player);
// 			System.out.println("Jogada do Bot");
// 			System.out.println(board);

// 			if (!board.endofGame() && board.getResult() == 'E') {
// 				System.out.println("Qual a sua jogada [0..8]:  ");
// 				int index = sc.nextInt();
// 				board.makeMove(index);
// 				System.out.println(board);
// 			}
// 		}
// 		sc.close();

// 		System.out.println("Board Final: ");
// 		System.out.println(board);
// 		board.printresults();
// 	}
// }


/**
 * Main do MCTS vs MCTSv2
 * 
 */

// public class Main {

// 	public static void main(String[] args) {
// 		// int [][] m= {{1,1,-1},
// 		// {-1,-1,1},
// 		// {-1,1,-1}};
// 		// Board board = new Board(m,-1);
// 		// System.out.println(board.getResult());
// 		// int m[][]= {{-1,0,0},
// 		// {0,1,0},
// 		// {0,0,0}};
// 		// Board board = new Board(m, -1);
// 		 int [][] m= {{0,0,0},
// 					{0,1,0},
// 					{0,0,0}};
// 		// Board board = new Board(m,-1);
// 		// System.out.println(board.getResult());
// 		int m1[][]= {{0,0,0},
// 					{0,-1,0},
// 					{0,0,0}};
// 		//Board boardtest = new Board(m, 'P');
// 		Board boardtest2 = new Board(m, 'B');
// 		int i = 0;
// 		int emp = 0;
// 		int first = 0;
// 		int second = 0;
// 		//int pf=0;
// 		while (i < 100) {
// 			Board board = new Board();
// 			// Scanner sc = new Scanner(System.in);
// 			char opponent = 'P';
// 			MCTS a = new MCTS();
// 			MCTSv2 b= new MCTSv2();
// 			while (board.getResult() == 'E' && !board.endofGame()) {
// 				if (opponent == 'B') {
// 					opponent = 'P';
// 				} else if (opponent == 'P') {
// 					opponent = 'B';
// 				}
// 				//board = (Board) a.solve(board, opponent);
// 				board = (Board) a.solve(board, opponent);
// 				//System.out.println(board);
// 				if (opponent == 'B') {
// 					opponent = 'P';
// 				} else if (opponent == 'P') {
// 					opponent = 'B';
// 				}

// 				board = (Board) a.solve(board, opponent);
// 			}


// 			if (board.getResult() == 'E')
// 				emp++;
// 			else if (board.getResult() == 'B')
// 				first++;
// 			else if (board.getResult() == 'P')
// 				second++;
// 			i++;
// 			System.out.println(board);
// 		}
// 		System.out.println("Empate: " + emp);
// 		System.out.println("First: " + first);
// 		System.out.println("Second: " + second);
// 		//System.out.println("pf: " + pf);
// 	}
// }
