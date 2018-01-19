package Gamestudio.service;

import Gamestudio.entity.Player;

public interface PlayerService {

	void register(Player player);

	Player login(String login, String password);

}