package Gamestudio.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import Gamestudio.entity.Rating;
import Gamestudio.service.RatingService;

@Transactional
public class RatingServiceJPA implements RatingService {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void setRating(Rating rating) {
		try {
			Rating r = (Rating) entityManager
					.createQuery("SELECT r FROM Rating r WHERE r.username = :username AND r.game = :game")
					.setParameter("username", rating.getUsername()).setParameter("game", rating.getGame())
					.getSingleResult();
			r.setValue(rating.getValue());
		} catch (NoResultException e) {
			entityManager.persist(rating);
		}

	}

	public double getAverageRating(String game) {
		Object o = entityManager.createQuery("SELECT AVG(r.value) FROM Rating r WHERE r.game = :game ")
				.setParameter("game", game).getSingleResult();
		return o == null ? 0 : (double) o;

	}

	public int getValue(String game, String username) {
		return (int) entityManager
				.createQuery("SELECT r.value FROM Rating r WHERE r.game = :game AND r.username = :username")
				.setParameter("game", game).setParameter("username", username).getSingleResult();
	}

}
