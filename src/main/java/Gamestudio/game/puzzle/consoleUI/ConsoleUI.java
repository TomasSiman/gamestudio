package Gamestudio.game.puzzle.consoleUI;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import Gamestudio.consoleui.ConsoleGameUI;
import Gamestudio.entity.Score;
import Gamestudio.game.puzzle.core.Field;
import Gamestudio.service.ScoreService;

public class ConsoleUI  implements ConsoleGameUI{
	private Field field;
	@Autowired
	private ScoreService scoreService;
	
	private Scanner scanner = new Scanner(System.in);
public ConsoleUI() {
	
}
	public void run() {
		field = new Field(5,5);
		print();
		
		long startTime = System.currentTimeMillis();
		while(!field.isSolved()) {
			
			processInput();
			print();
			
		}
	long finalTime = (System.currentTimeMillis() - startTime) / 1000;
		System.out.println("You won! in " + finalTime + " s."  );
		Score score = new Score();
		score.setGame(getName());
		score.setUsername(System.getProperty("user.name"));
		score.setValue((int)finalTime);
		scoreService.addScore(score);
		
	}

	private void processInput() {
		System.out.print("Enter tile number or X to exit: ");
		String input = scanner.nextLine().trim().toUpperCase();
		if("X".equals(input))
			System.exit(0);
		try {
			int tile = Integer.parseInt(input);
			if(!field.moveTile(tile)) {
				System.out.println("Really crazy?");
			}
		} catch (NumberFormatException e) {
			System.out.println("Are you crazy?");
		}
	}
	
	

	private void print() {
		
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				int tile = field.getTile(row, column);
				if (tile == Field.EMPTY_TILE)
					System.out.printf("   ", tile);
				else
					System.out.printf(" %2d", tile);
			}
			System.out.println();
		}
	}
	@Override
	public String getName() {
	
		return "puzzle";
	}
}
