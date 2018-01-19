package Gamestudio;

import java.util.Date;

import Gamestudio.entity.Comment;
import Gamestudio.entity.Rating;
import Gamestudio.entity.Score;
import Gamestudio.service.CommentService;
import Gamestudio.service.RatingService;
import Gamestudio.service.ScoreService;
import Gamestudio.service.impl.CommentServiceJDBC;
import Gamestudio.service.impl.RatingServiceJDBC;
import Gamestudio.service.impl.SORM;
import Gamestudio.service.impl.ScoreServiceJDBC;
import Gamestudio.service.impl.ScoreServiceSORM;

public class test {

	public static void main(String[] args) {
	Score score = new Score();
//	score.setUsername("Dominika");
//	score.setGame("mines");
//	score.setValue(100);
//	
	SORM sorm = new SORM();
//	sorm.insert(score);
	
	
	
//	ScoreService scoreService = new  ScoreServiceSORM();
//	System.out.println(sorm.select(Score.class));
	
	
	
	Comment comment = new Comment();
//	comment.setUsername("Jakub");
//	comment.setGame("mines");
//	comment.setContent("Hra bola na ... ");
//	 java.util.Date utilDate = new java.util.Date();
//	    java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
//	comment.setCreatedOn(sqlDate);
//	 
//	sorm.insert(comment);
	System.out.println(sorm.select(Comment.class));
	
//	CommentService commentService = new CommentServiceJDBC();
//	commentService.addComment(comment);
//	
//	System.out.println(commentService.getComments("mines"));

//		Rating rating = new Rating();
//		rating.setGame("mines");
//		rating.setUsername("Jakobo");
//		rating.setValue(5);
//		RatingService ratingService = new RatingServiceJDBC();
//		ratingService.setRating(rating);
//		
//	System.out.println(ratingService.getAverageRating("mines"));
//		
//		
	}

}
