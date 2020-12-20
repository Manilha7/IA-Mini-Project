import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Board board = new Board();
		Scanner sc = new Scanner(System.in);
	    int player1 = Board.P1;
	    int player2= Board.P2;
	    while(board.checkStatus() == -1) {
	    	board.performMove(player1, new Position(sc.nextInt(),sc.nextInt()));
	    	if(board.checkStatus() != -1) {
	    		board.printBoard();
	    		System.out.println("--------");
	    		break;
	    	}
	        board = MonteCarloTreeSearch.findNextMove(board, player2);
	        board.printBoard();
	        System.out.println("--------");
	    }
	    sc.close();
	    board.printStatus();
	}
		

	
	

}

