package ai2048;

import java.util.Random;

public class AI {
	private Board board;
	private final int NUM_RUNS = 1000;

	public AI(Board board) {
		this.board = board;
	}

	public boolean chooseBestMove() {
		int[] runTotals = new int[4]; // [left, right, up, down]
		int[] runTypeTotals = new int[4];

		for (int i = 0; i < NUM_RUNS; i++) {
			Board tempBoard = board.copyBoard();
			if (i % 4 == 0) {
				tempBoard.left();
				runTotals[0] += randomRun(tempBoard);
				runTypeTotals[0]++;
			}

			if (i % 4 == 1) {
				tempBoard.right();
				runTotals[1] += randomRun(tempBoard);
				runTypeTotals[1]++;

			}

			if (i % 4 == 2) {
				tempBoard.up();
				runTotals[2] += randomRun(tempBoard);
				runTypeTotals[2]++;

			}

			if (i % 4 == 3) {
				tempBoard.down();
				runTotals[3] += randomRun(tempBoard);
				runTypeTotals[3]++;
			}
		}

		int[] runAverages = { runTotals[0] / runTypeTotals[0], runTotals[1] / runTypeTotals[1],
				runTotals[2] / runTypeTotals[2], runTotals[3] / runTypeTotals[3] };

//		System.out.print(runAverages[0]);
//
//		for (int i = 1; i < 4; i++) {
//			System.out.print(", " + runAverages[i]);
//		}

		int index = 0;
		int max = runAverages[0];

		for (int i = 1; i < 4; i++) {
			if (runAverages[i] > max) {
				index = i;
				max = runAverages[i];
			}
		}

		//System.out.println(", " + max);

		if (index == 0)
			return board.left();
		if (index == 1)
			return board.right();
		if (index == 2)
			return board.up();
		if (index == 3)
			return board.down();
		return false;
	}

	private int randomRun(Board tempBoard) {
		Random randGen = new Random();

		int total = 0;
		while (tempBoard.canMove() && total < 400) {
			int move = randGen.nextInt(4);
			boolean change = false;
			total++;
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

			if (change)
				tempBoard.addTile();
		}

		//System.out.println(total);
		// System.out.println(tempBoard.getScore());
		return tempBoard.getScore();
	}
}
