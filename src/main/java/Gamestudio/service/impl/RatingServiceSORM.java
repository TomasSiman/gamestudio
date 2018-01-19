package Gamestudio.service.impl;

import java.util.List;

import Gamestudio.entity.Rating;
import Gamestudio.service.RatingService;

public class RatingServiceSORM implements RatingService {
	private SORM sorm = new SORM();

	@Override
	public void setRating(Rating rating) {

		sorm.insert(rating);
	}

	public double getAverageRating(String game) {
		List<Rating> list = sorm.select(Rating.class, String.format("WHERE game = '%s'", game));
		double r = 0;
		for (Rating rating : list) {
			r += rating.getValue();
		}
		return r / list.size();
	}

	@Override
	public int getValue(String username, String game) {
		// TODO Auto-generated method stub
		return 0;
	}

}
