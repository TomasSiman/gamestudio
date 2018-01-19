package Gamestudio.service.impl;

import java.util.List;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Favorite;
import Gamestudio.entity.Score;
import Gamestudio.service.CommentService;
import Gamestudio.service.FavoriteService;

public class FavoriteServiceSORM implements FavoriteService {
	private SORM sorm = new SORM();

	@Override
	public void setFavorite(Favorite favorite) {
		sorm.insert(favorite);
		
	}
	@Override
	public List<Favorite> getFavorite(String username) {
		
		return sorm.select(Favorite.class, String.format("WHERE username = '%s'", username));
	}




public void removeFavorite(String username, String game) {
	List<Favorite> result = sorm.select(Favorite.class,String.format("WHERE username = '%s' AND game = '%s'" , username, game));
	if(result.size() == 0) {
		
	}
		
	sorm.delete(result.get(0));
	
}

@Override
public boolean isFavorite(String username, String game) {

	return false;
}
	
}
