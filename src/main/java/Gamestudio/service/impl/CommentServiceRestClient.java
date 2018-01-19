package Gamestudio.service.impl;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Rating;
import Gamestudio.entity.Score;
import Gamestudio.service.CommentService;
import Gamestudio.service.RatingService;

public class CommentServiceRestClient implements CommentService {
	private static final String URL = "http://localhost:8080/rest/rating";

	@Override
	public void addComment(Comment comment) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(comment, MediaType.APPLICATION_JSON), Response.class);

	}

	@Override
	public List<Comment> getComments(String game) {
		Client client = ClientBuilder.newClient();
		return client.target(URL).path("/" + game).request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Comment>>() {
				});
	}

}
