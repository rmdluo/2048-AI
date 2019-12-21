package ai2048;

import java.util.Random;

public class AI {
	private Board board;
	private final int NUM_RUNS = 1000;

	public AI(Board board) {
		this.board = board;
	}

	public boolean chooseBestMove() {
		double[] runScoreTotals = new double[4]; // [left, right, up, down]
		double[] runTypeTotals = new double[4];

		for (int i = 0; i < NUM_RUNS; i++) {
			int score;
			Board tempBoard = board.copyBoard();

			int move = i;

			if (pastMoves[0] == pastMoves[1] && move == pastMoves[0])
				move = move + 1;

			move = move % 4;

			switch (move) {
			case 0:
				tempBoard.left();
				break;
			case 1:
				tempBoard.right();
				break;
			case 2:
				tempBoard.up();
				break;
			case 3:
				tempBoard.down();
				break;
			}

			score = randomRun(tempBoard);
			runTotals[move] += score;
			runTypeTotals[move]++;
		}

		double[] runAverages = { runTotals[0] / runTypeTotals[0], runTotals[1] / runTypeTotals[1],
				runTotals[2] / runTypeTotals[2], runTotals[3] / runTypeTotals[3] };

		int index = 0;
		double max = runAverages[0];

		for (int i = 1; i < 4; i++) {
			if (runAverages[i] > max) {
				index = i;
				max = runAverages[i];
			}
		}

		pastMoves[1] = pastMoves[0];
		pastMoves[0] = index;

		switch (index) {
		case 0:
			return board.left();
		case 1:
			return board.right();
		case 2:
			return board.up();
		case 3:
			return board.down();
		default:
			return false;
		}
	}

	private int randomRun(Board tempBoard) {
		Random randGen = new Random();

		while (tempBoard.canMove()) {
			int move = randGen.nextInt(4);
			boolean change = false;
			switch (move) {
			case 0:
				change = tempBoard.left();
				break;
			case 1:
				change = tempBoard.right();
				break;
			case 2:
				change = tempBoard.up();
				break;
			case 3:
				change = tempBoard.down();
				break;
			}

			if (change) {
				tempBoard.addTile();
			}
		}

		return tempBoard.getScore();
	}
}
