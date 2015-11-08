package core;

public class Player {
	/**
	 * Player class hold the data of one account
	 * It should include account_id, username, and current playing Character
	 */
	String username;
	Character character;
	int id;	// account id
	
	public Player() {
		character = new Character();
	}
	// Getters and Setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Character getCharacter() {
		return character;
	}
	public void setCharacter(Character character) {
		this.character = character;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
