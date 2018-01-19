package Gamestudio.service;

import java.util.List;

import Gamestudio.entity.Score;

public interface ScoreService {
	void addScore(Score score);
	
	List<Score> getTopScores(String game);
}
