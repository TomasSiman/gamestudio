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
import Gamestudio.game.puzzle.core.Field;
import Gamestudio.service.CommentService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PuzzleController extends AbstractGameController{
	Field field = new Field(2, 2);
	private double rating;
	private String game;
	
	public double getRating() {
		rating = ratingService.getAverageRating("puzzle");
		return rating;
	}

	public String getGame() {
		return game;
	}

	@RequestMapping("/puzzle")
	public String puzzle(@RequestParam(value = "tile", required = false) String tile, Model model) {
		field.gameStart();
		int tile2;
		try {
			tile2 = Integer.parseInt(tile);
			field.moveTile(tile2);

			if (field.isSolved()) {
				message = "Solved!!!";
				int time = (int) (System.currentTimeMillis()-field.getTime())/1000;
				scoreService.addScore(new Score(userController.getLoggedPlayer().getLogin(), "puzzle", time));
			}
		} catch (NumberFormatException e) {
			field = new Field(4, 4);
			message = "";
		}

		model.addAttribute("puzzleController", this);
		fillMethod(model);
		return "puzzle";
	}
	
	@RequestMapping("/commentPuzzle") 
	 public String comments(final Comment comment ,final Model model){
		comment.setGame("puzzle");
		  comment.setUsername(userController.getLoggedPlayer().getLogin());
		  comment.setCreatedOn(new Date());
		commentService.addComment(comment);
	  fillMethod(model);
	  return "puzzle";
	 }
	@RequestMapping(value = "/ratingPuzzle", method = { RequestMethod.GET }) 
	  public String rating(final Rating rating ,final Model model){
	  rating.setGame("puzzle");
	  rating.setUsername(userController.getLoggedPlayer().getLogin());
	    ratingService.setRating(rating);
	    fillMethod(model);
	   return "puzzle";
	  }
	
	@RequestMapping("/addFavorite_puzzle")
	public String addFavorite(Model model) {
		favoriteService.setFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "puzzle"));
		fillMethod(model);
		return "/puzzle";
	}

	public String render() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class='table'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>\n");
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);
				sb.append("<td>\n");
				if (!field.isSolved()) {
					sb.append(String.format("<a href='/puzzle?tile=%d'>\n", tile));
				}
				if (tile > 0) {
					sb.append("<font style='font-size:200%' class='tile'>"+ tile + "</font>");
				}
				if (!field.isSolved()) {
					sb.append("</a>\n");
				}

				sb.append("</td>\n");
			}
			sb.append("</tr>\n");
		}
		sb.append("</table>\n");

		return sb.toString();
	}


	@Override
	protected String getGameName() {
		return "puzzle";
	}

	@Override
	public boolean getFavorite() {
		return favoriteService.isFavorite(userController.getLoggedPlayer() != null ? userController.getLoggedPlayer().getLogin() : "", "puzzle");
	}

}
