package ai2048;

import java.util.Scanner;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

public class Board {
	private int boardDimension;
	private Tile[][] board;
	private int score;
	private int highest;

	public Board() {
		Point[] shape = {new Point(0, 0), new Point(0, 100), new Point(100, 100), new Point(100, 0)};
		boardDimension = 4;
		board = new Tile[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				board[i][j] = new Tile(shape, null, 0, 0);

		score = 0;
	}

	public Board(int boardDimension, Tile[][] board, int score) {
		this.boardDimension = boardDimension;
		this.board = board;
		this.score = score;
	}

	public Board(String filename) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new File(filename));
		score = Integer.parseInt(fileReader.nextLine());
		boardDimension = Integer.parseInt(fileReader.nextLine());
		loadBoardState(fileReader);
		fileReader.close();
	}

	/**
	 * gets board state
	 * <p>
	 * This method takes in a file reader.
	 * It reads the lines and makes a board representing it.
	 * It assumes that the textfile is formatted properly.
	 * </p>
	 * @author Raymond Luo
	 * @param fileReader The reader to read from
	 */
	private void loadBoardState(Scanner fileReader) {
		Point[] shape = {new Point(0, 0), new Point(0, 100), new Point(100, 100), new Point(100, 0)};
		String[][] tempBoard = new String[boardDimension][boardDimension];

		board = new Tile[boardDimension][boardDimension];
		for (int i = 0; i < boardDimension; i++) {
			tempBoard[i] = fileReader.nextLine().split(" ");
			for (int j = 0; j < boardDimension; j++) {
				if(tempBoard[i].length == 0)
					board[i][j] = new Tile(shape, null, 0, 0);
				else
					board[i][j] = new Tile(shape, null, 0, Integer.parseInt(tempBoard[i][j]));
			}
		}
	}

	/**
	 * resets board
	 * <p>This method resets the board.</p>
	 * @author Raymond Luo
	 */
	public void reset() {
		Point[] shape = {new Point(0, 0), new Point(0, 100), new Point(100, 100), new Point(100, 0)};

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = new Tile(shape, null, 0, 0);
			}
		}
	}

	/**
	 * counts empty spaces
	 * <p>This counts how many spaces are empty on a board.</p>
	 * @author Raymond Luo
	 * @return how many empty spaces there are
	 */
	public int countEmptySpaces() {
		int emptyTiles = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j].toString().equals("_"))
					emptyTiles++;
			}
		}

		return emptyTiles;
	}

	//gets the score
	public int getScore() {
		return score;
	}

	/**
	 * string representation
	 * <p>This method makes a string representation of the board.</p>
	 * @author Raymond Luo
	 */
	public String toString() {
		String stringRep = "";

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				stringRep += (board[i][j].toString() + "\t");
			}
			stringRep += "\n";
		}

		return stringRep;
	}

	/**
	 * checks if full
	 * <p>This method checks if the board is full.</p>
	 * @author Raymond Luo
	 * @return if the board is full.
	 */
	public boolean isFull() {
		int numEmpty = countEmptySpaces();
		if (numEmpty == 0)
			return true;
		else
			return false;
	}

	/**
	 * adds tile
	 * <p>This method adds a tile to a random square on the board.</p>
	 * @author Raymond Luo
	 */
	public void addTile() {
		if (!isFull()) {
			Point[] shape = {new Point(0, 0), new Point(0, 100), new Point(100, 100), new Point(100, 0)};

			Random randGen = new Random();
			Tile newTile;
			
			int row = randGen.nextInt(boardDimension);
			int column = randGen.nextInt(boardDimension);

			while (row < boardDimension && column < boardDimension && !board[row][column].toString().equals("_")) {
				column++;
				if (column == boardDimension) {
					column = 0;
					row++;
					
					if(row == boardDimension) {
						row = 0;
					}
				}
			}

			if (randGen.nextInt(100) + 1 <= 90)
				newTile = new Tile(shape, null, 0, 2);

			else
				newTile = new Tile(shape, null, 0, 4);

			if (row < boardDimension && column < boardDimension)
				board[row][column] = newTile;
		}
	}

	/**
	 * rotates clockwise
	 * <p>This method rotates the board 90 degrees clockwise.</p>
	 * @author Raymond Luo
	 */
	public void rotateCW() {
		Point[] shape = {new Point(0, 0), new Point(0, 100), new Point(100, 100), new Point(100, 0)};
		
		Tile[][] tempBoard = new Tile[boardDimension][boardDimension];
		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {
				if(!board[i][j].toString().equals("_"))
					tempBoard[j][boardDimension - i - 1] = new Tile(shape, null, 0, Integer.parseInt(board[i][j].toString()));
				else
					tempBoard[j][boardDimension - i - 1] = new Tile(shape, null, 0, 0);
			}
		}

		board = tempBoard;
	}

	/**
	 * checks if move
	 * <p>This method checks if the board can move.</p>
	 * @author Raymond Luo
	 * @return whether there is a move or not
	 */
	public boolean canMove() {
		if (countEmptySpaces() != 0)
			return true;

		for (Tile[] row : board) {
			for (int j = 0; j < boardDimension - 1; j++) {
				if (row[j].toString().equals(row[j + 1].toString()))
					return true;
			}
		}

		rotateCW();

		for (Tile[] row : board) {
			for (int j = 0; j < boardDimension - 1; j++) {
				if (row[j].toString().equals(row[j + 1].toString()))
					return true;
			}
		}

		for (int i = 0; i < 3; i++) {
			rotateCW();
		}

		return false;
	}

	/**
	 * moves tiles
	 * <p>This method combines and moves the tiles to the left.</p>
	 * @author Raymond Luo
	 * @return whether there was a change
	 */
	public boolean left() {
		Point[] shape = {new Point(0, 0), new Point(0, 100), new Point(100, 100), new Point(100, 0)};
		boolean change = false;
		boolean tilesAdded = false;
		for (int i = 0; i < boardDimension; i++) {
			Tile[] row = new Tile[4];
			int newRowIndex = 0;

			for (int j = 0; j < boardDimension; j++) {
				Tile currentTile;
				if(!board[i][j].toString().equals("_"))
					currentTile = new Tile(shape, null, 0, board[i][j].getValue());
				else
					currentTile = new Tile(shape, null, 0, 0);
				
				if (!(currentTile.toString().equals("_"))) {
					if (newRowIndex != 0) {
						if (currentTile.toString().equals(row[newRowIndex - 1].toString()) && !tilesAdded) {
							newRowIndex--;
							row[newRowIndex] = new Tile(shape, null, 0, 2 * currentTile.getValue());
							score += 2 * currentTile.getValue();
							if(2 * currentTile.getValue() > highest) highest = 2 * currentTile.getValue();
							tilesAdded = true;
						} else {
							row[newRowIndex] = currentTile;
							tilesAdded = false;
						}
					} else {
						row[newRowIndex] = currentTile;
						tilesAdded = false;
					}

					newRowIndex++;
				}
			}

			while (newRowIndex < boardDimension) {
				row[newRowIndex] = new Tile(shape, null, 0);
				newRowIndex++;
			}

			for (int j = 0; j < row.length; j++) {
				if (!(row[j].toString().equals(board[i][j].toString()))) {
					change = true;
					board[i] = row.clone();
				}
			}
		}

		return change;
	}

	/**
	 * moves tiles
	 * <p>This method combines and moves the tiles to the right.</p>
	 * @author Raymond Luo
	 * @return whether there was a change
	 */
	public boolean right() {
		boolean change;
		rotateCW();
		rotateCW();
		change = left();
		rotateCW();
		rotateCW();
		return change;
	}

	/**
	 * moves tiles
	 * <p>This method combines and moves the tiles to the up.</p>
	 * @author Raymond Luo
	 * @return whether there was a change
	 */
	public boolean up() {
		boolean change;
		rotateCW();
		rotateCW();
		rotateCW();
		change = left();
		rotateCW();
		return change;
	}

	/**
	 * moves tiles
	 * <p>This method combines and moves the tiles to the down.</p>
	 * @author Raymond Luo
	 * @return whether there was a change
	 */
	public boolean down() {
		boolean change;
		rotateCW();
		change = left();
		rotateCW();
		rotateCW();
		rotateCW();
		return change;
	}

	/**
	 * copies board
	 * <p>This method copies the board.</p>
	 * @author Raymond Luo
	 * @return the copy of the board
	 */
	public Board copyBoard() {
		Point[] shape = {new Point(0, 0), new Point(0, 100), new Point(100, 100), new Point(100, 0)};
		Tile[][] newBoard = new Tile[boardDimension][boardDimension];

		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {
				String tileString = board[i][j].toString();
				if (tileString.equals("_"))
					newBoard[i][j] = new Tile(shape, null, 0);
				else
					newBoard[i][j] = new Tile(shape, null, 0, Integer.parseInt(tileString));
			}
		}

		return new Board(boardDimension, newBoard, score);
	}
	
	/**
	 * checks if player wins
	 * <p>This method checks if the player has won.</p>
	 * @author Raymond Luo
	 * @return if the player has won
	 */
	public boolean checkWin() {
		for (int i = 0; i < boardDimension; i++) {
			for (int j = 0; j < boardDimension; j++) {
				String tileString = board[i][j].toString();
				if (tileString.equals("2048"))
					return true;
			}
		}
		
		return false;
	}
	
	public int getHighest() {
		return highest;
	}
	
	/**
	 * draws board
	 * <p>This method draws the board.</p>
	 * @author Raymond Luo
	 * @param brush the brush to draw with
	 */
	public void paint(Graphics brush) {
		for(int i = 0; i < boardDimension; i++) {
			for(int j = 0; j < boardDimension; j++) {
				int[] xCoords = {0, 100 * (j + 1), 100 * (j + 1), 0};
				int[] yCoords = {0, 0, 100 * (i + 1), 100 * (i + 1)};
				brush.drawPolygon(xCoords, yCoords, 4);
				Point position = new Point((j * 100) + 50, (i * 100) + 50);
				board[i][j].setPosition(position);
				board[i][j].paint(brush);
			}
		}		
	}
}
