package Gamestudio.service;

import Gamestudio.entity.Rating;

public interface RatingService {


	void setRating(Rating rating);
	
	double getAverageRating(String game);
	
	
}
