package uk.ac.cam.ird28;

public class User {
	private String name;
	private String password;
	private int wins;
	private int draws;
	private int losses;
	
	public User(String n, String p) {
		name = n;
		password = p;
		wins = 0;
		draws = 0;
		losses = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getWins() { return wins; }
	public int getDraws() { return draws; }
	public int getLosses() { return losses; }
	
	public void incWins() {wins++;}
	public void incDraws() {draws++;}
	public void incLosses() {losses++;}
	
	
	
	
	public boolean checkPassword(String input) {
		return input.equals(password);
	}
	
}
