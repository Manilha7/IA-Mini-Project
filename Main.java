import java.util.Scanner;


//public class Main {

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
// 		int i = 0;
// 		int emp = 0;
// 		int first = 0;
// 		int second = 0;
// 		while (i < 100) {
// 			Board board = new Board();
// 			// Scanner sc = new Scanner(System.in);
// 			char opponent = 'P';
// 			while (board.getResult() == 'E' && !board.endofGame()) {
// 				MCTS a = new MCTS();
// 				board = (Board) a.solve(board, opponent);
// 				//System.out.println(board);
// 				if (opponent == 'B') {
// 					opponent = 'P';
// 				} else if (opponent == 'P') {
// 					opponent = 'B';
// 				} else {
// 					System.out.println("erro");
// 				}

// 			}
// 			//System.out.println(board.getResult());
// 			//System.out.println("------------");
// 			// System.out.println(board);
// 			// sc.close();
// 			// System.out.println("Board Final: ");
// 			//System.out.println(board);
// 			// board.printresults();
// 			if (board.getResult() == 'E')
// 				emp++;
// 			else if (board.getResult() == 'B')
// 				first++;
// 			else if (board.getResult() == 'P')
// 				second++;
// 			i++;
// 			board= new Board();
// 		}
// 		System.out.println("Empate: " + emp);
// 		System.out.println("First: " + first);
// 		System.out.println("Second: " + second);
// 	}
// }

public class Main {

	public static void main(String[] args) {
		// int [][] m= {{1,1,-1},
		// {-1,-1,1},
		// {-1,1,-1}};
		// Board board = new Board(m,-1);
		// System.out.println(board.getResult());
		// int m[][]= {{-1,0,0},
		// {0,1,0},
		// {0,0,0}};
		// Board board = new Board(m, -1);
		Board board = new Board();
		Scanner sc = new Scanner(System.in);
		while (board.getResult() == 'E' && !board.endofGame()) {

			System.out.println("Qual a sua jogada: ");
			int index = sc.nextInt();
			board.makeMove(index);
			System.out.println(board);

			if (!board.endofGame() && board.getResult() == 'E') {
				MCTS a = new MCTS();
				char player = 'P';
				board = (Board) a.solve(board, player);
				System.out.println("Jogada do Bot");
				System.out.println(board);
			}
		}
		sc.close();
		System.out.println("Board Final: ");
		System.out.println(board);
		board.printresults();
	}
}