package Gamestudio.service;

import java.sql.SQLException;

import Gamestudio.entity.Rating;

public interface RatingService {


	void setRating(Rating rating);
	
	double getAverageRating(String game);
	
	int getValue(String username, String game);
}
