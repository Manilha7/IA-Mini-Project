import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class Board implements Ilayout, Cloneable {

	private int size = 3;
	private int[][] board = new int[size][size];
	private int player;
	/*
	 * -1| 1| 1 1| -1| 1 1| 1|-1
	 * 
	 * 0 --> - 1 --> X -1 --> O
	 * 
	 * Player uses "X" Bot uses "O"
	 * 
	 * P --> Player wins D --> Draw B --> Bot wins
	 * 
	 */

	public Board(int[][] board, int player) {

		try {

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (board[i][j] != 0 && board[i][j] != 1 && board[i][j] != -1) {
						throw new IllegalArgumentException();
					} else {
						this.board = board;
					}
				}
			}
			this.player=player;
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid argument in table");
			System.exit(1);
		}
	}

	public Board() {
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	public int getPlayer() {
		return this.player;
	}

	@Override
	public boolean equals(Object t) {
		int[][] l1 = ((Board) t).board;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				if (l1[i][j] != board[i][j]) {
					return false;
				}
		}
		return true;
	}

	public boolean isGoal(Ilayout l) {
		return equals(l);
	}

	public Board clone() {

		Board cpy = new Board();
		Board cpy2 = new Board(this.board,this.player);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cpy.board[i][j] = cpy2.board[i][j];
			}
		}
		cpy.player=this.player;
		return cpy;
	}

	/**
	 * usado para adicionar adicionar 1 sempre que se aumenta a profundidade das
	 * geracoes
	 * 
	 * @return retorna 1 sempre que e chamado
	 */
	@Override
	public double getG() {
		return 1.0;
	}

	/**
	 * imprime em formato string
	 * 
	 * @return retorna uma string
	 */
	@Override
	public String toString() {

		String result = "";

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == -1)
					result += "X ";

				else if (board[i][j] == 1)
					result += "O ";

				else
					result += "- ";
			}
			result += "\n";
		}
		return result;
	}

	public double getResult() {

		Board copy = this.clone();
		int rows = copy.board.length;
		int cols = copy.board[0].length;
		int sumRow = 0;
		int sumCol = 0;
		int sumDiag = 0;
		int sumAntiDiag = 0;
		double result = 0.5;



		//Checking for rows and columns for a possible win 
		for (int i = 0; i < rows; i++) {
			sumCol = 0;
			for (int j = 0; j < cols; j++) {
				sumRow = sumRow + copy.board[i][j];
				sumCol = sumCol + copy.board[j][i];
			}

			if (sumRow == 3 || sumCol == 3) {
				result = 1.0;
				break;
			}

			else if (sumRow == -3 || sumCol == -3) {
				result = 0.0;
				break;
			}

		}


		//If no win on a row or a column then check the diagonal
		if (result != 1.0 && result != 0.0) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (i == j) {
						sumDiag = sumDiag + copy.board[i][j];
					}

					if (sumDiag == 3) {
						result = 1.0;
						break;
					} else if (sumDiag == -3) {
						result = 0.0;
						break;
					}

				}
			}
		}

		//If the diagonal neither have a win for someone then the anti-diagonal is checked
		else if (result != 1.0 && result != 0.0) {

			for (int i = 0, j = cols - 1; i < rows && j >= 0; i++, j--) {
				sumAntiDiag = sumAntiDiag + copy.board[i][j];

				if (sumAntiDiag == 3) {
					result = 1.0;
					break;
				} else if (sumAntiDiag == -3) {
					result = 0.0;
					break;
				}
			}

		}

		/*//If there´s no win after all of the possibilities then for sure the board is filled to is max and a draw is declared
		if (result != 1.0 && result != 0.0) {
			result = 0.5;
		}*/

		return result;
	}

	public void makeMove(int index, int player){
		this.player=player;
		try{
		
		if(index == 0){
			if(this.board[0][0] == 0)
				this.board[0][0] = -1;
			
			else {throw new IllegalArgumentException();}
		}
		else if(index == 1){
			if(this.board[0][1] == 0)
				this.board[0][1] = -1;
		
			else{throw new IllegalArgumentException();}
		}
		else if(index == 2){
			if(this.board[0][2] == 0)
				this.board[0][2] = -1;
			
			else{throw new IllegalArgumentException();}
		}
		else if(index == 3){
			if(this.board[1][0] == 0)
				this.board[1][0] = -1;
			
			else{throw new IllegalArgumentException();}
		}
		else if(index == 4){
			if(this.board[1][1] == 0)
				this.board[1][1] = -1;
			
			else{throw new IllegalArgumentException();}
		}
		else if(index == 5){
			if(this.board[1][2] == 0)
				this.board[1][2] = -1;
			
			else{throw new IllegalArgumentException();}
		}
		else if(index == 6){
			if(this.board[2][0] == 0)
				this.board[2][0] = -1;
			
			else{throw new IllegalArgumentException();}
		}
		else if(index == 7){
			if(this.board[2][1] == 0)
				this.board[2][1] = -1;
			
			else{throw new IllegalArgumentException();}
		}
		else if(index == 8){
			if(this.board[2][2] == 0)
				this.board[2][2] = -1;
		
			else{throw new IllegalArgumentException();}
		}

	}
	catch(IllegalArgumentException e){
		Scanner scn = new Scanner(System.in);
		System.out.println("Invalid position inserted, Type a valid position : ");
		index = scn.nextInt();
		makeMove(index,player);
		scn.close();
		
	}

	}


	@Override
	public List<Ilayout> children() {

		List<Ilayout> list = new ArrayList<Ilayout>();
		Board copy2 = this.clone();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!list.contains(copy2)) {
					if (copy2.board[i][j] == 0) {
						if (copy2.player== -1){
							copy2.board[i][j] = 1;
						}else{
							copy2.board[i][j] = -1;
						}
						copy2.player= 2- copy2.player;
						list.add(copy2);
						copy2 = this.clone();
					}
				}
			}
		}
		return list;
	}

}