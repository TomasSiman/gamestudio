package Gamestudio.game.guessNumber.consoleUI;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import Gamestudio.consoleui.ConsoleGameUI;
import Gamestudio.entity.Score;
import Gamestudio.service.ScoreService;

public class ConsoleUI implements ConsoleGameUI {

	private Scanner sc = new Scanner(System.in);
	@Autowired
	private ScoreService scoreService;

	public void run() {
		long startTime = System.currentTimeMillis();
		
		int inputNumber = 0;
		int randomNumber = (int) (Math.random() * 10) + 1;
		while (inputNumber != randomNumber) {
			System.out.println("Enter the Number");
			try {
				String inputString = sc.nextLine();
				if (inputString.toLowerCase().equals("x")) {
					System.exit(0);
					
				}
				inputNumber = Integer.parseInt(inputString);
				if (inputNumber == randomNumber) {
					System.out.println("CONGRATULATIONS!!! You hit the Number");
					long finalTime = (System.currentTimeMillis() - startTime) / 1000;
					Score score = new Score();
					score.setGame(getName());
					score.setUsername(System.getProperty("user.name"));
					score.setValue((int) finalTime);
					scoreService.addScore(score);
				} else if (inputNumber > randomNumber) {
					System.out.println("Higher!");
					
				} else if (inputNumber < randomNumber) {
					System.out.println("Lower!");
				
				}
			} catch (NumberFormatException e) {
				System.out.println("You must enter the Number !!");
			}
		}
	}

	@Override
	public String getName() {

		return "guessNumber";
	}

}
