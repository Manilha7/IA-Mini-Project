import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		// int [][] m= {{1,1,-1},
		// 			 {-1,-1,1},
		// 			{-1,1,-1}};
		// Board board = new Board(m,-1);
		// System.out.println(board.getResult());
		Board board = new Board();
		Scanner sc = new Scanner(System.in);
		while (board.getResult()==0.5) {
			System.out.println("Qual a sua jogada: ");
			int index =sc.nextInt();
			board.makeMove(index);
			MCTS a=new MCTS();
			board= a.solve(board);
			System.out.println(board);
		}
	    }
	    

}

