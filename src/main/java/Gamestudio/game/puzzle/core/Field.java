package Gamestudio.game.puzzle.core;

import java.util.Random;

import Gamestudio.game.puzzle.consoleUI.ConsoleUI;

public class Field {
	private final int rowCount;
	private final int columnCount;
	private final int[][] tiles;
	public static final int EMPTY_TILE = 0;
	private long time = 0;
	

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new int[rowCount][columnCount];
		generate();
	}
	public void gameStart() {
		if(time == 0) {
		time = System.currentTimeMillis();
		}	
	}
	
	public long getTime() {
		return time;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getTile(int row, int column) {
		return tiles[row][column];
	}

	private void generate() {
		int tile = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = tile;
				tile++;
			}
		}
		tiles[rowCount - 1][columnCount - 1] = EMPTY_TILE;
		shuffle();

	}

	private void shuffle() {
		Random random = new Random();
		for (int i = 0; i < rowCount * columnCount * 100; i++) {
			moveTile(random.nextInt(rowCount * columnCount - 1) + 1);
		}
	}
	
		
	

	

	private Coordinate getTileCoordinate(int tile) {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == tile) {
					return new Coordinate(row, column);
				}
			}
		}
		return null;
	}

	public boolean isSolved() {
		int tile = 1;
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] != EMPTY_TILE)
					if (tiles[row][column] != tile)
						return false;
				tile++;
			}
		}
		return true;
	}

	public boolean moveTile(int tile) {
		if (tile <= 0 || tile >= rowCount * columnCount)
			return false;

		Coordinate tileCoordinate = getTileCoordinate(tile);
		Coordinate emptyCoordinate = getTileCoordinate(EMPTY_TILE);

		if ((tileCoordinate.getRow() == emptyCoordinate.getRow()
				&& Math.abs(tileCoordinate.getColumn() - emptyCoordinate.getColumn()) == 1)
				|| (tileCoordinate.getColumn() == emptyCoordinate.getColumn()
						&& Math.abs(tileCoordinate.getRow() - emptyCoordinate.getRow()) == 1)) {
			tiles[emptyCoordinate.getRow()][emptyCoordinate.getColumn()] = tile;
			tiles[tileCoordinate.getRow()][tileCoordinate.getColumn()] = EMPTY_TILE;
			return true;
		}

		return false;
	}

	
}
