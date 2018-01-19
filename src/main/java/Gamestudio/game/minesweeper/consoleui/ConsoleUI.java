package Gamestudio.game.minesweeper.consoleui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import Gamestudio.consoleui.ConsoleGameUI;
import Gamestudio.entity.Score;
import Gamestudio.game.minesweeper.core.Clue;
import Gamestudio.game.minesweeper.core.Field;
import Gamestudio.game.minesweeper.core.GameState;
import Gamestudio.game.minesweeper.core.Mine;
import Gamestudio.game.minesweeper.core.Tile;
import Gamestudio.service.ScoreService;

public class ConsoleUI  implements ConsoleGameUI{

	private Field field ;
	private Scanner scanner;
	private static final Pattern INPUT_PATTERN = Pattern.compile("([OM])([A-I])([1-9])");
	
	@Autowired
	private ScoreService scoreService;
	
	public ConsoleUI()  {
		 
		
		scanner = new Scanner(System.in);
	}
	
	public void run() {
		field = new Field(9,9,1);
		print();
		
		long startTime = System.currentTimeMillis();
		while(field.getState() == GameState.PLAYING) {
			processInput();
			print();
		}
		long finalTime = (System.currentTimeMillis() - startTime) / 1000;
		if(field.getState() == GameState.SOLVED) {
			System.out.println("Gratulujem vyhral si v case " + finalTime + " s.");
			Score score = new Score();
			score.setGame(getName());
			score.setUsername(System.getProperty("user.name"));
			score.setValue((int)finalTime);
			scoreService.addScore(score);
		} else if(field.getState() == GameState.FAILED) {
			System.out.println("Prehral si v case " + finalTime + " s.");
		}

	}
	
	
	
	private void processInput() {
		System.out.print("Zadaj cinnost (MA1, OC4, X) ");
		String input = scanner.nextLine().trim().toUpperCase();
		
		if("X".equals(input)) {
			System.exit(0);
		}
		
		Matcher matcher = INPUT_PATTERN.matcher(input);
		if(matcher.matches()) {
			int row = matcher.group(2).charAt(0) - 'A';
			int column = Integer.parseInt(matcher.group(3)) - 1;

			if("O".equals(matcher.group(1))) {
				field.openTile(row, column);
			} else {
				field.markTile(row, column);
			}
		} else {
			System.out.println("Zle zadany vstup!");	
		}
	}

	private void print() {
		System.out.print("  ");
		for (int column = 1; column <= field.getColumnCount(); column++) {
			System.out.print(column + " ");
		}
		System.out.println();
		
		for (int row = 0; row < field.getRowCount(); row++) {
			System.out.print((char)(row + 'A'));
			for (int column = 0; column < field.getColumnCount(); column++) {
				System.out.print(" ");
				Tile tile = field.getTile(row, column);
				switch (tile.getState()) {
				case CLOSED:
					System.out.print('-');
					break;
				case MARKED:
					System.out.print('M');
					break;
				case OPEN:
					if (tile instanceof Mine) {
						System.out.print('X');
					} else if (tile instanceof Clue) {
						System.out.print(((Clue) tile).getValue());
					}
					break;
				}
			}
			System.out.println();
		}
	}

	@Override
	public String getName() {
		
		return "mines";
	}
}
