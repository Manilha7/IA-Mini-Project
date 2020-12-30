import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



class Board implements Ilayout, Cloneable {

	private int size = 3;
	private int[][] board = new int[size][size];
	private char player;
	private int movecount;

	/**
	 * Construtor da Board a partir de uma matrix e um player
	 * @param board array de inteiros representando o jogo
	 * @param player char que representa o player a qual a jogada pertence
	 */
	public Board(int[][] board, char player) {

		try {

			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (board[i][j] != 0 && board[i][j] != 1 && board[i][j] != -1) {
						throw new IllegalArgumentException();
					} else {
						if (board[i][j] == 1 && board[i][j] == -1) {
							this.movecount++;
						}
						this.board = board;
					}
				}
			}
			this.player = player;
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid argument in table");
			System.exit(1);
		}
	}

	/**
	 * Construtor da Board vazia
	 */
	public Board() {
	}


	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
	
	public char getPlayer() {
		return this.player;
	}

	public void setPlayer(char player) {
		this.player= player;
	}


	@Override
	/**
	 * Verificacao se duas Boards sao iguais
	 * @param t Object em que e feito cast para Board
	 * @return verdadeiro ou falso consoante o resultado da comparacao
	 */
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

	
	/**
	 * cria um clone de uma Board
	 * @return Um clone da Board atual
	 */
	public Board clone() {

		Board cpy = new Board();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cpy.board[i][j] = this.board[i][j];
			}
		}
		cpy.player=this.player;
		cpy.movecount=this.movecount;
		return cpy;
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


	
	/**
	 * Calcula o resultado da Board atual
	 * @return char que representa o resultado da Board atual
	 */
	public char getResult() {

		Board copy = this.clone();
		int rows = copy.board.length;
		int cols = copy.board[0].length;
		int sumRow = 0;
		int sumCol = 0;
		int sumDiag = 0;
		int sumAntiDiag = 0;
		char result = 'E';



		//Checking for rows and columns for a possible win 
		for (int i = 0; i < rows; i++) {
			sumCol = 0;
			sumRow=0;
			for (int j = 0; j < cols; j++) {
				sumRow = sumRow + copy.board[i][j];
				sumCol = sumCol + copy.board[j][i];
			}

			if (sumRow == 3 || sumCol == 3) {
				result = 'B';
				break;
			}

			else if (sumRow == -3 || sumCol == -3) {
				result = 'P';
				break;
			}

		}


		//If no win on a row or a column then check the diagonal
		if (result == 'E') {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (i == j) {
						sumDiag = sumDiag + copy.board[i][j];
						
					}

					if (sumDiag == 3) {
						result = 'B';
						break;
					} else if (sumDiag == -3) {
						result = 'P';
						break;
					}

				}
			}
		}

		//If the diagonal neither have a win for someone then the anti-diagonal is checked
		if (result == 'E') {

			for (int i = 0, j = cols - 1; i < rows && j >= 0; i++, j--) {
				sumAntiDiag = sumAntiDiag + copy.board[i][j];
				if (sumAntiDiag == 3) {
					result = 'B';
					break;
				} else if (sumAntiDiag == -3) {
					result = 'P';
					break;
				}
			}
		}
		return result;


	}

	/**
	 * Em caso de jogar contra um player, funcao que traduz a jogada do player para matrix
	 * @param index inteiro que indica onde o player jogou
	 */
	public void makeMove(int index){
		this.player='P';
		this.movecount++;
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
		makeMove(index);
		scn.close();
		
	}
	}


	/**
	 * Print dos resultados dos jogos
	 */
	public void printresults(){
		if (this.getResult()=='B') {
			System.out.println("Jogo ganho pelo Bot!");
		}
		if (this.getResult()=='P') {
			System.out.println("Jogo ganho pelo Player!");
		}
		if (this.getResult()=='E') {
			System.out.println("Jogo Empatado!");
		}
		System.exit(0);
	}

	@Override
	/**
	 * Verifica se a Board esta completa
	 * @return true se estiver completa
	 * @return false se nao estiver completa
	 */
	public boolean endofGame() {
		if (this.movecount < 9) {
			return false;
		}
		return true;
	}

	@Override
	/**
	 * Gera os filhos da Board atual
	 * @return Lista com os filhos e o respetivo player e numero de movimentos.
	 */
	public List<Ilayout> children() {

		List<Ilayout> list = new ArrayList<Ilayout>();
		Board copy2 = this.clone();
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!list.contains(copy2)) {
					if (copy2.board[i][j] == 0) {
						if (copy2.player== 'P'){
							copy2.board[i][j] = 1;
						}else{
							copy2.board[i][j] = -1;
						}
						if (copy2.player=='B') {
							copy2.setPlayer('P');
						}else{
							copy2.setPlayer('B');
						}
						copy2.movecount++;
						//System.out.println(copy2.getPlayer());
						list.add(copy2);
						copy2 = this.clone();
						//System.out.println(copy2.player);
					}
				}
			}
		}
		return list;
	}

}