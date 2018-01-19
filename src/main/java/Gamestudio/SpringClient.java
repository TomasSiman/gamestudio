package Gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import Gamestudio.consoleui.ConsoleGameUI;
import Gamestudio.consoleui.ConsoleMenu;
import Gamestudio.game.minesweeper.consoleui.ConsoleUI;
import Gamestudio.service.CommentService;
import Gamestudio.service.FavoriteService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;
import Gamestudio.service.impl.CommentServiceJPA;
import Gamestudio.service.impl.CommentServiceRestClient;
import Gamestudio.service.impl.FavoriteServiceJPA;
import Gamestudio.service.impl.FavoriteServiceRestClient;
import Gamestudio.service.impl.RatingServiceJPA;
import Gamestudio.service.impl.RatingServiceRestClient;
import Gamestudio.service.impl.ScoreServiceJPA;
import Gamestudio.service.impl.ScoreServiceRestClient;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = { "gamestudio" },
excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "gamestudio.server.*"))

public class SpringClient {

	public static void main(String[] args) {

//		SpringApplication.run(SpringClient.class, args);
		new SpringApplicationBuilder(SpringClient.class).web(false).run(args);
	}
	@Bean
	public CommandLineRunner runner(ConsoleMenu menu) {
		return args -> menu.show();
	}
	
	@Bean
	public ConsoleMenu menu(ConsoleGameUI[] games) {
		return new ConsoleMenu(games);

	}


	@Bean
	public ConsoleGameUI consoleUIMines() {
		return new ConsoleUI();
	}



	// puzzle

	@Bean
	public ConsoleGameUI ConsoleUIPuzzle() {
		return new Gamestudio.game.puzzle.consoleUI.ConsoleUI();
	}


	@Bean
	public RatingService ratingService() {
//		return new RatingServiceRestClient();
		return new RatingServiceJPA();
	}
	@Bean
	public ScoreService scoreService() {
//		return new ScoreServiceJPA();
		return new ScoreServiceRestClient();
	}
	@Bean
	public CommentService commentService() {
//		return new CommentServiceJPA();
		return new CommentServiceRestClient();
	}
	@Bean
	public FavoriteService favoriteService() {
		return new FavoriteServiceRestClient();
		//return new FavoriteServiceJPA();
	}
	@Bean
	public ConsoleGameUI ConsoleUIGuessNumber(){
		return new Gamestudio.game.guessNumber.consoleUI.ConsoleUI();
	}
}
