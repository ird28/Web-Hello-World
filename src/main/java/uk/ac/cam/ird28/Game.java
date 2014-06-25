package uk.ac.cam.ird28;

public class Game {
	
	private int[] board;
	
	public Game() {
		board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = 0;
		}
	}
	
	public Game(int[] initialBoard) {
		board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = initialBoard[i];
		}
	}
	
	private static String getSym(int x) {
		if (x==0) return ". .";
		if (x==1) return "O";
		if (x==2) return "X";
		return "FAIL"; // should never happen
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			result.append(getSym(board[i])+" ");
			if (i%3 == 2) result.append("<br>");
		}
		return result.toString();
	}
	
	public void update(int space, int value) {
		board[space] = value;
	}
	
	public int getSpace(int s) {
		if (s<0 || s >= 9) return -1;
		return board[s];
	}
	
	public int getRandMove() {
		assert !fullBoard();
		int guess = (int) (9.0*Math.random());
		while (board[guess] != 0) {
			guess = (int) (9.0*Math.random());
		}
		return guess;
	}
	
	public int getOkayMove() {
		assert !fullBoard();
		for (int i = 0; i<9; i++) { // win if possible
			if (board[i]==0) {
				Game h = new Game(board);
				h.update(i, 2);
				if (h.hasWon(2)) return i;
			}
		}
		for (int i = 0; i<9; i++) { // prevent win if possible
			if (board[i]==0) {
				Game h = new Game(board);
				h.update(i, 1);
				if (h.hasWon(1)) return i;
			}
		}
		if (board[4]==0) return 4; //centre if possible
		return getRandMove(); // random
	}
	
	public boolean fullBoard() {
		for (int i = 0; i < 9; i++) {
			if (board[i]==0) return false;
		}
		return true;
	}

	public boolean hasWon(int player) {
		assert player != 0;
		boolean a = board[0] == player;
		boolean b = board[1] == player;
		boolean c = board[2] == player;
		boolean d = board[3] == player;
		boolean e = board[4] == player;
		boolean f = board[5] == player;
		boolean g = board[6] == player;
		boolean h = board[7] == player;
		boolean i = board[8] == player;
		boolean rows = (a&&b&&c)||(d&&e&&f)||(g&&h&&i);
		boolean cols = (a&&d&&g)||(b&&e&&h)||(c&&f&&i);
		boolean diags = (a&&e&&i)||(c&&e&&g);
		return rows||cols||diags;
	}

}
