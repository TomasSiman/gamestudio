package Gamestudio.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
CREATE TABLE comment(
    ident INT PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    game VARCHAR(32) NOT NULL,
    content VARCHAR(128)NOT NULL,
    createdOn TIMESTAMP NOT NULL
    );
*/
@Entity
public class Comment {
	@Id
	@GeneratedValue
	private int ident;
	private String username;

	private String game;

	
	
	private String content;
	
	private Date createdOn;
public Comment() {
		
	}
	
	public Comment(String username, String game, String content) {
		this.username = username;
		this.game = game;
		this.content = content;
		this.createdOn =  new java.sql.Timestamp(new Date().getTime());
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedOn() {

		return createdOn;
	}

	@Override
	public String toString() {
		return "Comment [ident=" + ident + ", username=" + username + ", game=" + game + ", content=" + content
				+ ", createdOn=" + createdOn + "]\n";
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}