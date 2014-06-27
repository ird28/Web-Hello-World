package uk.ac.cam.ird28;

public class Game {
	
	private int[] board;
	private String status;
	public boolean easyUsed;
		
	public Game() {
		status = "c";
		board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = 0;
		}
		easyUsed = false;
	}
	
	public Game(int[] initialBoard) {
		status = "c";
		board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = initialBoard[i];
		}
		easyUsed = false;
	}
	
	public int[] getBoard() { return board; }
	
	public String[] getSymBoard() {
		String[] result = new String[9];
		for (int i=0; i<9; i++) {
			result[i] = getSym(board[i]);
		}
		return result;
	}
	
	public String getStatus() { return status; }
	
	public String getEasyUsed() { return easyUsed ? "yes":"no"; }
	
	private static String getSym(int x) {
		if (x==0) return " ";
		if (x==1) return "O";
		if (x==2) return "X";
		return "FAIL"; // should never happen
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			result.append(getSym(board[i]));
		}
		return result.toString();
	}
	
	public void update(int space, int player) {
		assert (player==1 || player==2);
		board[space] = player;
		if (hasWon(1)) status = "w";
		else if (hasWon(2)) status = "l";
		else if (fullBoard()) status = "d";
	}
	
	public int getSpace(int s) {
		if (s<0 || s >= 9) return -1;
		return board[s];
	}
	
	public int computeRandMove() {
		assert !fullBoard();
		int guess = (int) (9.0*Math.random());
		while (board[guess] != 0) {
			guess = (int) (9.0*Math.random());
		}
		return guess;
	}
	
	public int computeOkayMove() {
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
		return computeRandMove(); // random
	}
	
	public int computeBestMove() { // recurses too deeply to work
		assert !fullBoard();
		if (spacesOnBoard()==8) {
			if (board[4]==0) return 4;
			assert board[0] == 0;
			return 0;
		}
		int draw = -1;
		for (int i=0; i < 9; i++) {
			int result = evaluatePositionFor(2);
			if (result==1) return i;
			if (result==0) draw = i;
		}
		if (draw == -1) return computeRandMove();
		return draw;
	}
	
	private int evaluatePositionFor(int player) { // +1 for win, 0 for draw, -1 for loss
		int other = 1;
		if (player==1) other = 2;
		int best = -1;
		for (int i=0; i<9; i++) {
			Game h = new Game(board);
			h.update(i, player);
			if (h.hasWon(player)) return 1;
			int result = -h.evaluatePositionFor(other);
			if (result > best) {
				best = result;
				if (best == 1) return best;
			}
		}
		return best;
	}
	
	public int spacesOnBoard() {
		int result = 0;
		for (int i = 0; i < 9; i++) {
			if (board[i]==0) result++;
		}
		return result;
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
