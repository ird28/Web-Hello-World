package uk.ac.cam.ird28;

public class User {
	private String name;
	private int passhash;
	private int wins;
	private int draws;
	private int losses;
	
	public User(String n, String p) {
		name = n;
		passhash = p.hashCode();
		wins = 0;
		draws = 0;
		losses = 0;
	}
	
	public User(String n, int ph, int w, int d, int l) {
		name = n;
		passhash = ph;
		wins = w;
		draws = d;
		losses = l;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPasswordHash() {
		return passhash;
	}
	
	public int getWins() { return wins; }
	public int getDraws() { return draws; }
	public int getLosses() { return losses; }
	
	public void incWins() {wins++;}
	public void incDraws() {draws++;}
	public void incLosses() {losses++;}
	
	
	
	
	public boolean checkPassword(String input) {
		return passhash == input.hashCode();
	}
	
}
