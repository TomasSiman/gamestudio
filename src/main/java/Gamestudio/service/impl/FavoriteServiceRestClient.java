package Gamestudio.service.impl;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Favorite;
import Gamestudio.service.FavoriteService;

public class FavoriteServiceRestClient implements FavoriteService {
	private static final String URL = "http://localhost:8080/rest/favorite";


	@Override
	public void setFavorite(Favorite favorite) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(favorite, MediaType.APPLICATION_JSON), Response.class);
		
	}

	@Override
	public List<Favorite> getFavorite(String username) {
		Client client = ClientBuilder.newClient();
		return client.target(URL).path("/" + username).request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Favorite>>() {
				});
		
	}
	
	@Override
	public boolean isFavorite(String username, String game) {
	
		return false;
	}



}
