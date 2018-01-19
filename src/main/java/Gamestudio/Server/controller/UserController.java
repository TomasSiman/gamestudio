package Gamestudio.Server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import Gamestudio.entity.Player;
import Gamestudio.service.PlayerService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
	@Autowired
	private PlayerService playerService;

	private Player loggedPlayer;

	public Player getLoggedPlayer() {
		return loggedPlayer;
	}
	@RequestMapping("/user")
	public String user(Model model) {
		return "login";
	}

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("/login")
	public String login(Player player, Model model) {
		loggedPlayer = playerService.login(player.getLogin(), player.getPassword());
		return isLogged() ? "index" : "login";
	}

	@RequestMapping("/register")
	public String register(Player player, Model model) {
		playerService.register(player);
		login(player, model);
		return "/login";
	}

	@RequestMapping("/logout")
	public String login(Model model) {
		loggedPlayer = null;

		return "login";
	}

	public boolean isLogged() {
		return loggedPlayer != null;
	}
}
