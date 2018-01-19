package Gamestudio.service.impl;

import java.util.List;

import Gamestudio.entity.Comment;
import Gamestudio.service.CommentService;

public class RatingServiceSORM implements CommentService {
	private SORM sorm = new SORM();

	@Override
	public void addComment(Comment comment) {
		
		sorm.insert(comment);
	}

	@Override
	public List<Comment> getComments(String game) {
		
		return sorm.select(Comment.class, String.format("WHERE game = '%s' ORDER BY value DESC LIMIT 10", game));
	}



	
}
