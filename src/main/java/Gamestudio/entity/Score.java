package Gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 CREATE TABLE score(
     ident INT PRIMARY KEY,
     username VARCHAR(32) NOT NULL,
     game VARCHAR(32) NOT NULL,
     value INTEGER NOT NULL
     );
 */
@Entity

public class Score {
	@Id
	@GeneratedValue
	private int ident;


	private String username;

	private String game;

	private int value;
	
	public Score() {
		
	}

	public Score(String username, String game, int value) {
		this.username = username;
		this.game = game;
		this.value = value;
	}

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Score [ident=" + ident + ", username=" + username + ", game=" + game + ", value=" + value + "]\n";
	}

	

	
}
