package Gamestudio.service.impl;


import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Gamestudio.entity.Score;
import Gamestudio.service.ScoreService;

public class ScoreServiceRestClient implements ScoreService {
	private static final String URL = "http://localhost:8080/rest/score";

	@Override
	public void addScore(Score score) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(score, MediaType.APPLICATION_JSON), Response.class);
	}

	@Override
	public List<Score> getTopScores(String game) {
		Client client = ClientBuilder.newClient();
		return client.target(URL).path("/" + game).request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Score>>() {});
	}
}

