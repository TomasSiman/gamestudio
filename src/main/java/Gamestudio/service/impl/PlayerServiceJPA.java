package Gamestudio.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import Gamestudio.entity.Player;
import Gamestudio.service.PlayerService;

@Transactional
public class PlayerServiceJPA implements PlayerService {
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see Gamestudio.service.impl.PlayerService#register(Gamestudio.entity.Player)
	 */
	@Override
	public void register(Player player) {
		entityManager.persist(player);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Gamestudio.service.impl.PlayerService#login(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Player login(String login, String password) {
		try {
			return (Player) entityManager
					.createQuery("SELECT p FROM Player p WHERE p.login = :login AND p.password = :password")
					.setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
			return null;

		}

	}

}
