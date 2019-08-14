package ai2048;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Game2048View implements KeyListener {
	private Board board;
	private boolean change;

	public Game2048View(Board board) {
		this.board = board;
		change = false;
	}

	//sets board
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * gets user interation
	 * <p>
	 * This method gets a user's text input and returns the corresponding constant.
	 * </p>
	 * @author Raymond Luo
	 * @return The user's action
	 */
	public UserAction getUserAction() {
		Scanner reader = new Scanner(System.in);
		String action = reader.next();

		switch (action.toLowerCase()) {
		case "w":
			return UserAction.UP;
		case "a":
			return UserAction.LEFT;
		case "s":
			return UserAction.DOWN;
		case "d":
			return UserAction.RIGHT;
		}
		return null;
	}

	/**
	 * updates display
	 * <p>This method displays the board in the console.</p>
	 * @author Raymond Luo
	 */
	public void updateDisplay() {
		System.out.println(board.toString());
	}

	//gets change
	public boolean getChange() {
		return change;
	}
	
	//sets change
	public void setChange(boolean change) {
		this.change = change;
	}

	/**
	 * does nothing
	 * <p>This method does nothing.</p>
	 * @author Raymond Luo
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * checks key presses
	 * <p>This method makes moves based on key presses.</p>
	 * @author Raymond Luo
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case 38:
			change = board.up();
			break;
		case 37:
			change = board.left();
			break;
		case 40:
			change = board.down();
			break;
		case 39:
			change = board.right();
			break;
		case 81:
			System.exit(0);
			break;
		case 82:
			board.reset();
			change = true;
			break;
		}

	}

	/**
	 * does nothing
	 * <p>This method does nothing.</p>
	 * @author Raymond Luo
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
