package Gamestudio.consoleui;

import java.sql.SQLException;
import java.util.Scanner;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Favorite;
import Gamestudio.entity.Rating;
import Gamestudio.service.CommentService;
import Gamestudio.service.FavoriteService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;

public class ConsoleMenu {
	private ConsoleGameUI[] games;
	@Autowired
	private RatingService ratingService;

	@Autowired
	private ScoreService scoreService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FavoriteService favoriteService;
	private Scanner scanner = new Scanner(System.in);

	public ConsoleMenu(ConsoleGameUI[] games) {
		this.games = games;
	}

	public void show() throws IllegalAccessException, SQLException {
		while (true) {
			// System.out.println(scoreService.getTopScores("mines"));

			// scoreService.addScore(new Score("sudoku","Jakub", 6));

			// System.out.println(commentService.getComments("mines"));
//			 favoriteService.setFavorite(new Favorite("jakobo", "sudoku"));
			// favoriteService.removeFavorite(new Favorite("puzzle", "Jakub"));
			// commentService.addComment(new Comment("jakobo", "mines", "hra bola dobra"));
			// ratingService.setRating(new Rating("Daniel","puzzle",3));
			//
			// System.out.println(ratingService.getValue("Jakub", "puzzle"));
			System.out.println("-----");
			int index = 0;
			for (ConsoleGameUI game : games) {
				double rating = ratingService.getAverageRating(game.getName());
				System.out.printf("%d. %s (%.2f/5)\n", index + 1, game.getName(), rating);
				++index;
			}
			System.out.println("----------------------------");

			do {
				System.out.println("Select a game : ");
				String line = scanner.nextLine().trim();
				if (line.toLowerCase().equals("x")) {
					System.exit(0);
				}
				try {
					index = Integer.parseInt(line);
				} catch (NumberFormatException e) {
					index = -1;
				}
				int nameIndex = 0;
				for (ConsoleGameUI game : games) {
					nameIndex++;
					if (line.equals(game.getName())) {
						index = nameIndex;
						break;
					}

				}

			} while (index < 1 || index > games.length);

			games[index - 1].run();
			String name = System.getProperty("user.name");
			String game = games[index - 1].getName();

			System.out.println("Top Score " + scoreService.getTopScores(games[index - 1].getName()));
			try {
				System.out.printf("Your rating is %d", ratingService.getValue(name, game));
				System.out.println();
			} catch (NoResultException e) {
				System.out.println("No ratings yet");
			}
			System.out.println("Add rating for this game : ");
			try {
				String ratingValue = scanner.nextLine().trim();

				if (!("".equals(ratingValue))) {
					int intValue = Integer.parseInt(ratingValue);
					ratingService.setRating(new Rating(name, game, intValue));

				}
			} catch (NumberFormatException e) {
				System.out.println("Write only numbers 1-5");
			}

			System.out.println(commentService.getComments(game));
			System.out.println("Add comment for this game: ");
			String comment = scanner.nextLine();
			if (!("".equals(comment))) {
				commentService.addComment(new Comment(name, game, comment));

			}

		}
	}

}
