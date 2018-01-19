package Gamestudio.service;

import java.util.List;

import Gamestudio.entity.Favorite;

public interface FavoriteService {

	
	void setFavorite(Favorite favorite);
	
	List<Favorite> getFavorite(String username);
	
	boolean isFavorite(String username, String game);	

}
