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
import Gamestudio.game.minesweeper.core.Clue;
import Gamestudio.game.minesweeper.core.Field;
import Gamestudio.game.minesweeper.core.GameState;
import Gamestudio.game.minesweeper.core.Tile;
import Gamestudio.game.minesweeper.core.TileState;
import Gamestudio.service.CommentService;
import Gamestudio.service.FavoriteService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class MinesController {
	private Field field = new Field(9, 9, 1);
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

	private boolean marking;
	private String message;
	private double rating;
	private String game;
	
	public boolean getFavorite() {
		return favoriteService.isFavorite(userController.getLoggedPlayer() != null ? userController.getLoggedPlayer().getLogin() : "", "mines");
	}

	public double getRating() {
		rating = ratingService.getAverageRating("mines");
		return rating;
	}

	public String getGame() {
		return game;
	}

	public boolean isMarking() {
		return marking;
	}

	public String getMessage() {
		return message;
	}

	@RequestMapping("/mines_mark")
	public String mines(Model model) {
		marking = !marking;
		fillMethod(model);
		return "mines";
	}

	@RequestMapping("/mines")
	public String mines(@RequestParam(value = "row", required = false) String row,
			@RequestParam(value = "column", required = false) String column, Model model) {
		processCommand(row, column);
		fillMethod(model);
		return "mines";
	}
	
	@RequestMapping("/commentMines") 
	 public String comments(final Comment comment ,final Model model){
		comment.setGame("mines");
		  comment.setUsername(userController.getLoggedPlayer().getLogin());
		  comment.setCreatedOn(new Date());
		commentService.addComment(comment);
	  fillMethod(model);
	  return "mines";
	 }
	
	@RequestMapping("/addFavorite_mines")
	public String addFavorite(Model model) {
		favoriteService.setFavorite(new Favorite(userController.getLoggedPlayer().getLogin(), "mines"));
		fillMethod(model);
		return "/mines";
	}
	
	@RequestMapping(value = "/ratingMines", method = { RequestMethod.GET }) 
	  public String rating(final Rating rating ,final Model model){
	  rating.setGame("mines");
	  rating.setUsername(userController.getLoggedPlayer().getLogin());
	    ratingService.setRating(rating);
	    fillMethod(model);
	   return "mines";
	  }

	public String render() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table class='center'>\n");
		for (int row = 0; row < field.getRowCount(); row++) {
			sb.append("<tr>");
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				String image = "closed";
				switch (tile.getState()) {
				case CLOSED:
					image = "closed";
					break;
				case MARKED:
					image = "marked";
					break;
				case OPEN:
					if (tile instanceof Clue) {
						image = "open" + ((Clue) tile).getValue();
					} else
						image = "mine";

				}

				sb.append("<td>\n");
				if (tile.getState().equals(TileState.OPEN) || !(field.getState() == GameState.PLAYING)) {
					sb.append("<img src='/images/mines/" + image + ".png' height=\"25\" width=\"25\">");

				} else {
					sb.append(String.format("<a href='/mines?row=%d&column=%d'>\n", row, column));
					sb.append("<img src='/images/mines/" + image + ".png' height=\"25\" width=\"25\">");
					sb.append("</a>\n");
				}
				sb.append("</td>\n");
			}
			sb.append("</tr>\n");
		}

		sb.append("</table>\n");
		return sb.toString();
	}

	private void createField() {
		field = new Field(9, 9, 1);

		message = "";
	}

	private void fillMethod(Model model) {
		model.addAttribute("minesController", this);
		model.addAttribute("scores", scoreService.getTopScores("mines"));
		model.addAttribute("comment", commentService.getComments("mines"));
		model.addAttribute("userController", userController);
		model.addAttribute("favorite", getFavorite());
		
	}

	private void processCommand(String row, String column) {
		try {
			if (marking) {
				field.markTile(Integer.parseInt(row), Integer.parseInt(column));
			} else {
				field.openTile(Integer.parseInt(row), Integer.parseInt(column));
			}
			if (field.getState() == GameState.FAILED) {
				message = "Failed :-(";
			} else if (field.getState() == GameState.SOLVED) {
				if (userController.isLogged()) {
					int time = (int) (field.getEndTime() - field.getStartTime()) / 1000;
					Score score = new Score();
					score.setGame("mines");
					try {
						score.setUsername(userController.getLoggedPlayer().getLogin());
					} catch (NullPointerException e) {
						// e.printStackTrace();
					}
					score.setValue(time);
					scoreService.addScore(score);
				}
				message = "Solved!!!";

			}
		} catch (NumberFormatException e) {
			createField();
		}
	}

}
