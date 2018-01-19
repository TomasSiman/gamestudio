package Gamestudio.Server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import Gamestudio.service.CommentService;
import Gamestudio.service.FavoriteService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;

public abstract class AbstractGameController {
	@Autowired
	protected ScoreService scoreService;
	@Autowired
	protected UserController userController;
	@Autowired
	protected RatingService ratingService;
	@Autowired
	protected CommentService commentService;
	@Autowired
	protected FavoriteService favoriteService;
	
	protected String message;
	
	public abstract boolean getFavorite();
	
	public String getMessage() {
		return message;
	}
	
	protected void fillMethod(Model model) {
		model.addAttribute("puzzleController", this);
		model.addAttribute("scores", scoreService.getTopScores(getGameName()));
		model.addAttribute("comment", commentService.getComments(getGameName()));
		model.addAttribute("userController", userController);
		model.addAttribute("favorite", getFavorite());
	}
	protected abstract String getGameName();
}
