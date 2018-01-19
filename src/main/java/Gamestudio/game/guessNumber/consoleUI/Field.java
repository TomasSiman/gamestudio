package Gamestudio.game.guessNumber.consoleUI;

import Gamestudio.Server.controller.GuessNumberController;

public class Field  {

	long startTime;
int number;
	long endTime;
	private int randomNumber;
	private int difficult;
	private String hint;
	GuessNumberController controller;
	
	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}
	
	public String getHint() {
		return hint;
	}
	
	public Field(int difficult) {
		this.difficult = difficult;
		run();
	}
	public Field() {
	}

	public void run() {
		startTime = System.currentTimeMillis();
		randomNumber = (int) (Math.random() * difficult) + 1;
	}

	public void procces(int inputNumber) {
		number = inputNumber;
		if(isSolved()) {
			hint = "You Guess the Number!!!";
			endTime = System.currentTimeMillis();
			
		
		}
		if(inputNumber > randomNumber) {
			hint = inputNumber +" is too High!";
		}
		if(inputNumber < randomNumber) {
			hint = inputNumber +" is too Low!";
		}
			
	}	
	public int getRandomNumber() {
		return randomNumber;
	}
	public int getNumber() {
		return number;
	}
public boolean isSolved() {
	if(randomNumber== number) {
		return true;
	}
	return false;
	
}
	
}
