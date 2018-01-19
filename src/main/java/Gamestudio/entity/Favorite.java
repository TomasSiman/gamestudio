package Gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"username" , "game"})})
public class Favorite {
	
	@Id
	@GeneratedValue
	private int ident;
	
	
	private String username;

	private String game;
	
public Favorite() {
		
	}
	
	public Favorite(String username, String game) {
		this.username = username;
		this.game = game;
		
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

	@Override
	public String toString() {
		return "Favorite [ident=" + ident + ", username=" + username + ", game=" + game + "]\n";
	}

	

	
	

	
	
}
