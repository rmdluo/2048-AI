package ai2048;

import java.io.FileNotFoundException;

/** 
* 2048 game
*
* This program is based on the game 2048.
* The goal is to combine numbers until the player gets 2048.
* <p>
* ADSB PS12: 2048
* 10/14/2018
* @author Raymond Luo
*/

public class Game2048 {
	/**
	 * main method
	 * <p>This method starts the game.</p>
	 * @author Raymond Luo
	 * @param args Command line arguments
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Board board = new Board();
		Game2048View view = new Game2048View(board);
		Game2048Controller controller = new Game2048Controller(board, view);
		controller.runGame();
	}
}
