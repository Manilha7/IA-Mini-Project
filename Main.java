import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		// int [][] m= {{1,1,-1},
		// {-1,-1,1},
		// {-1,1,-1}};
		// Board board = new Board(m,-1);
		// System.out.println(board.getResult());
		Board board = new Board();
		Scanner sc = new Scanner(System.in);
		while (board.getResult() == 0.5 && !board.endofGame()) {
			System.out.println("Qual a sua jogada: ");
			int index = sc.nextInt();
			board.makeMove(index);
			System.out.println(board);
			if (!board.endofGame()) {
				MCTS a = new MCTS();
<<<<<<< HEAD
				board = (Board) a.solve(board);
				System.out.println("Jogada do Bot");
=======
				board = a.solve(board);
>>>>>>> parent of 60f6e4d... up change return solve
				System.out.println(board);
			}
		}
		sc.close();
		System.out.println("Board Final: ");
		System.out.println(board);
		board.printresults();
	}
}

