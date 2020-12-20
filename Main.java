import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		int index= 5;
		board.makeMove(index, -1);
		MCTS a=new MCTS();
		board= a.solve(board);
		System.out.println(board);
	    }
	    

}

