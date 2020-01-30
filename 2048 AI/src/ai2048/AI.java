package ai2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AI {
	private Board board;
	private final int NUM_RUNS = 1000;
	private int[] pastMoves;

	public AI(Board board) {
		this.board = board;
		pastMoves = new int[2];
		pastMoves[0] = -1;
		pastMoves[1] = -1;
	}

	public boolean chooseBestMove() {
		double[] runTotals = new double[4]; // [left, right, up, down]
		double[] runTypeTotals = new double[4];

		List<Integer> moves = new ArrayList<Integer>();
		moves.add(0);
		moves.add(1);
		moves.add(2);
		moves.add(3);

		for (int i = 0; i < NUM_RUNS / 4; i++) {
			Runnable move1 = () -> {
				int move = 0;
				int score;
				Board tempBoard = board.copyBoard();

				if (pastMoves[0] == pastMoves[1] && move == pastMoves[0])
					move = move + 1;

				tempBoard.left();

				score = randomRun(tempBoard);
				runTotals[move] += score;
				runTypeTotals[move]++;
			};
			
			Runnable move2 = () -> {
				int move = 1;
				int score;
				Board tempBoard = board.copyBoard();

				if (pastMoves[0] == pastMoves[1] && move == pastMoves[0])
					move = move + 1;

				tempBoard.right();

				score = randomRun(tempBoard);
				runTotals[move] += score;
				runTypeTotals[move]++;
			};
			
			Runnable move3 = () -> {
				int move = 2;
				int score;
				Board tempBoard = board.copyBoard();

				if (pastMoves[0] == pastMoves[1] && move == pastMoves[0])
					move = move + 1;

				tempBoard.up();

				score = randomRun(tempBoard);
				runTotals[move] += score;
				runTypeTotals[move]++;
			};
			
			Runnable move4 = () -> {
				int move = 3;
				int score;
				Board tempBoard = board.copyBoard();

				if (pastMoves[0] == pastMoves[1] && move == pastMoves[0])
					move = 0;

				tempBoard.down();

				score = randomRun(tempBoard);
				runTotals[move] += score;
				runTypeTotals[move]++;
			};
			
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			Thread moveThread1 = new Thread(move1);
			Thread moveThread2 = new Thread(move2);
			Thread moveThread3 = new Thread(move3);
			Thread moveThread4 = new Thread(move4);
			
			executor.execute(moveThread1);
			executor.execute(moveThread2);
			executor.execute(moveThread3);
			executor.execute(moveThread4);
			
			executor.shutdown();	
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

		// int total = 0;
		// int numMoves = 0;
		while (tempBoard.canMove()) {// && total < 400) {
			int move = randGen.nextInt(4);
			boolean change = false;
			// total++;
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
				// numMoves++;
			}
		}

		// also possible to use numMoves instead of just the score
		return tempBoard.getScore();// numMoves;
	}
}
