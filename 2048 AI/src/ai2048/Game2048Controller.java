package ai2048;

import java.awt.Color;
import java.awt.Graphics;

public class Game2048Controller extends Game {
	private Board board;
	private boolean change;
	
	public Game2048Controller(Board board) {
		super("2048", 400, 420);

		this.setFocusable(true);
		this.requestFocus();

		this.board = board;
	}

	/**
	 * runs game
	 * <p>This method runs the game.</p>
	 * @author Raymond Luo
	 */
	public void runGame() {
		board.addTile();
		repaint();
	}

	/**
	 * displays games
	 * <p>
	 * This method displays the game board.
	 * </p>
	 * @author Raymond Luo
	 * @param brush the brush to draw with
	 */
	@Override
	public void paint(Graphics brush) {
		brush.setColor(Color.WHITE);
		brush.fillRect(0, 0, 400, 420);
		brush.setColor(Color.BLACK);
		
		AI ai = new AI(board);
		
		board.paint(brush);
		if (board.canMove()) {

			if (change) {
				brush.setColor(Color.WHITE);
				brush.fillRect(0, 0, 500, 500);
				brush.setColor(Color.BLACK);
				board.addTile();
				board.paint(brush);
				change = false;
			}
			
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			change = ai.chooseBestMove();
		} else {
			System.out.println("Score: " + board.getScore());
			System.out.println("Highest Tile: " + board.getHighest());
			//System.out.println("Game Over. No more possible Moves.");
			System.exit(0);
		}

	}
}
