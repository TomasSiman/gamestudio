package Gamestudio.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import Gamestudio.entity.Comment;
import Gamestudio.service.CommentService;
@Transactional
public class CommentServiceJPA implements CommentService {
	
	@PersistenceContext
	private EntityManager entityManager;
	

	@Override
	public void addComment(Comment comment) {
		
		entityManager.persist(comment);
	}

	@Override
	public List<Comment> getComments(String game) {
		
		return entityManager.createQuery("SELECT c FROM Comment c WHERE c.game = :game").setParameter("game", game).getResultList();
	}



	
}
