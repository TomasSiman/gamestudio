package Gamestudio.service;

import java.util.List;

import Gamestudio.entity.Comment;

public interface CommentService {
	void addComment(Comment comment);
	List<Comment> getComments(String game);
}
