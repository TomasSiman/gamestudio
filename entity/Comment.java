package Gamestudio.entity;

import java.util.Date;

/*
CREATE TABLE comment(
    ident INT PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    game VARCHAR(32) NOT NULL,
    content VARCHAR(128)NOT NULL,
    createdOn TIMESTAMP NOT NULL
    );
*/

public class Comment {
	private String username;

	private String game;

	private int ident;
	
	private String content;
	
	private Date createdOn;

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

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	@Override
	public String toString() {
		return "Comment username=" + username + ", game=" + game + ", ident=" + ident + ", content = " + content
				+ ", createdOn = " + createdOn + "\n";
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

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
