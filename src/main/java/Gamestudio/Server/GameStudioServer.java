package Gamestudio.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import Gamestudio.service.CommentService;
import Gamestudio.service.FavoriteService;
import Gamestudio.service.PlayerService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;
import Gamestudio.service.impl.CommentServiceJPA;
import Gamestudio.service.impl.FavoriteServiceJPA;
import Gamestudio.service.impl.PlayerServiceJPA;
import Gamestudio.service.impl.RatingServiceJPA;
import Gamestudio.service.impl.ScoreServiceJPA;

@SpringBootApplication
// @EnableWs
@EntityScan({ "Gamestudio.entity" })
public class GameStudioServer {
	public static void main(String[] args) {
		SpringApplication.run(GameStudioServer.class, args);
	}

	@Bean
	public ScoreService scoreService() {
		return new ScoreServiceJPA();
	}

	@Bean
	public CommentService commentService() {
		return new CommentServiceJPA();
	}

	@Bean
	public RatingService ratingService() {
		return new RatingServiceJPA();
	}

	@Bean
	public FavoriteService favoriteService() {
		return new FavoriteServiceJPA();
	}
	@Bean
	public PlayerService playerService() {
		return new PlayerServiceJPA();
	}
}
