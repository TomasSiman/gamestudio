package Gamestudio.Server.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Favorite;
import Gamestudio.entity.Rating;
import Gamestudio.entity.Score;
import Gamestudio.game.guessNumber.consoleUI.Field;
import Gamestudio.service.CommentService;
import Gamestudio.service.FavoriteService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class GuessNumberController {
	private Field field;
	private String message;
	private double rating;
	private String difficult;
	private int difficultScore;
	int inputInt;
	private int finalScore;
	private String game;

	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserController userController;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FavoriteService favoriteService;

	public double getRating() {
		rating = ratingService.getAverageRating("guessNumber");
		return rating;
	}
	
	public String getMessage() {
		return message;
	}

	public String getDifficult() {
		return difficult;
	}
	
	public String getGame() {
		return game;
	}
	
	public boolean getFavorite() {
		return favoriteService.isFavorite(userController.getLoggedPlayer() != null ? userController.getLoggedPlayer().getLogin() : "", "guessNumber");
	}
	
	@RequestMapping("/guessNumber")
	public String guessNumber(Model model) {
		field = new Field(100);
		difficult = "1 - 100";
		message = "";
		fillMethod(model);
		difficultScore = 2;
		return "guessNumber";
	}
	
	@RequestMapping("/commentGuessNumber") 
	 public String comments(final Comment comment ,final Model model){
		comment.setGame("guessNumber");
		  comment.setUsername(userController.getLoggedPlayer().getLogin());
		  comment.setCreatedOn(new Date());
		commentService.addComment(comment);
	  fillMethod(model);
	  return "guessNumber";
	 }
	
	@RequestMapping(value = "/ratingguessNumber", method = { RequestMethod.GET }) 
	  public String rating(final Rating rating ,final Model model){
	  rating.setGame("guessNumber");
	  rating.setUsername(userController.getLoggedPlayer().getLogin());
	    ratingService.setRating(rating);
	    fillMethod(model);
	   return "guessNumber";
	  }

	@RequestMapping("/guessNumber_easy")
	public String guessNumberEasy(Model model) {
		field = new Field(10);
		difficult = "1 - 10";
		fillMethod(model);
		difficultScore = 1;
		return "guessNumber";
	}

	@RequestMapping("/guessNumber_hard")
	public String guessNumberHard(Model model) {
		field = new Field(1000);
		difficult = "1 - 1000";
		fillMethod(model);
		difficultScore = 3;
		return "guessNumber";
	}

	@RequestMapping("/guessNumber_ask")
	public String guessNumber(@RequestParam(value = "guess", required = false) String guess, Model model) {

		if (!guess.isEmpty()) {
			try {
				inputInt = Integer.parseInt(guess);
			} catch (NumberFormatException e) {
			message = "You have to enter number";
				
			}
			field.procces(inputInt);
			if(userController.isLogged() && field.isSolved()) {
				saveScore();
			}
			message = field.getHint();
		} 
		fillMethod(model);
		return "/guessNumber";
	}

	
	@RequestMapping("/addFavorite_guess")
	public String addFavorite(Model model) {
		favoriteService.setFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "guessNumber"));
		fillMethod(model);
		return "/guessNumber";
	}

	private void fillMethod(Model model) {
		model.addAttribute("guessNumberController", this);
		model.addAttribute("scores", scoreService.getTopScores("guessNumber"));
		model.addAttribute("comment", commentService.getComments("guessNumber"));
		model.addAttribute("userController", userController);
		model.addAttribute("favorite", favoriteService.isFavorite(userController.getLoggedPlayer() != null ? userController.getLoggedPlayer().getLogin() : "", "guessNumber"));
	}

	
	
	public void saveScore() {		
			int time = (int) (field.getEndTime() - field.getStartTime()) / 1000;
			if (difficultScore == 1) {
				finalScore = 300 - time;
			}
			if (difficultScore == 2) {
				finalScore = 500 - time;
			}
			if (difficultScore == 3) {
				finalScore = 1000 - time;
			}
			Score score = new Score();
			score.setGame("guessNumber");
			score.setUsername(userController.getLoggedPlayer().getLogin());
			score.setValue(finalScore);
			scoreService.addScore(score);
		
	}

	

	
}