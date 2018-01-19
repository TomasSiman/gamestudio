package Gamestudio.game.minesweeper.core;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import Gamestudio.Server.controller.UserController;
import Gamestudio.service.ScoreService;

public class Field {
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private UserController userController;
	long startTime;
	

	

	long endTime;
	private final int rowCount;

	private final int columnCount;

	private final int mineCount;

	private GameState state = GameState.PLAYING;

	private final Tile[][] tiles;

	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		startTime = System.currentTimeMillis();
		generate();
	}

	private void generate() {
		
		generateMines();
		fillWithClues();

	}

	private void generateMines() {
		int minesToSet = mineCount;
		Random random = new Random();

		while (minesToSet > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				minesToSet--;
			}
		}
	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null)
					tiles[row][column] = new Clue(countNeighbourMines(row, column));
			}
		}
	}

	private int countNeighbourMines(int row, int column) {
		int mines = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						if (tiles[aRow][aColumn] instanceof Mine)
							mines++;
					}
				}
			}
		}
		return mines;
		// int mines = 0;
		// if (isMineAt(row - 1, column - 1))
		// mines++;
		// if (isMineAt(row - 1, column))
		// mines++;
		// if (isMineAt(row - 1, column + 1))
		// mines++;
		// if (isMineAt(row, column - 1))
		// mines++;
		// if (isMineAt(row, column + 1))
		// mines++;
		// if (isMineAt(row + 1, column - 1))
		// mines++;
		// if (isMineAt(row + 1, column))
		// mines++;
		// if (isMineAt(row + 1, column + 1))
		// mines++;
		// return mines;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public long getEndTime() {
		return endTime;
	}

	private boolean isMineAt(int row, int column) {
		return row >= 0 && row < rowCount && column >= 0 && column < columnCount && tiles[row][column] instanceof Mine;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public void markTile(int row, int column) {
		if (state == GameState.PLAYING) {
			Tile tile = tiles[row][column];

			if (tile.getState() == TileState.CLOSED) {
				tile.setState(TileState.MARKED);
			} else if (tile.getState() == TileState.MARKED) {
				tile.setState(TileState.CLOSED);
			}
		}
	}

	public void openTile(int row, int column) {
		if (state == GameState.PLAYING) {
			Tile tile = tiles[row][column];

			if (tile.getState() == TileState.CLOSED) {
				tile.setState(TileState.OPEN);

				if (tile instanceof Mine) {
					state = GameState.FAILED;
					return;
				}

				if (((Clue) tile).getValue() == 0)
					openNeighbourTiles(row, column);

				if (isSolved()) {
					state = GameState.SOLVED;
					return;
				}
			}
		}
	}

	private void openNeighbourTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int aRow = row + rowOffset;
			if (aRow >= 0 && aRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int aColumn = column + columnOffset;
					if (aColumn >= 0 && aColumn < columnCount) {
						openTile(aRow, aColumn);
					}
				}
			}
		}
	}

	private boolean isSolved() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				Tile tile = tiles[row][column];
				if (tile instanceof Clue && tile.getState() != TileState.OPEN)
					return false;
			}
		}
		endTime = System.currentTimeMillis();
		return true;
	}

	
}
