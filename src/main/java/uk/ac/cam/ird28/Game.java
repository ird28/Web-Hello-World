package uk.ac.cam.ird28;

public class Game {
	
	private int[] board;
	
	public Game() {
		board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = 0;
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
	
	public String getSpace(int s) {
		return getSym(board[s]);
	}
	
	public int getRandMove() {
		boolean isSpace = false;
		for (int i = 0; i < 9; i++) {
			if (board[i]==0)
				isSpace = true;
		}
		assert isSpace;
		int guess = (int) (9.0*Math.random());
		while (board[guess] != 0) {
			guess = (int) (9.0*Math.random());
		}
		return guess;
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
