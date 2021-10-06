package ai2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that has the AI and chooses the best moves.
 */
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

	//Does the calculations for which move would be the best move
	public boolean chooseBestMove() {
		double[] runTotals = new double[4]; // [left, right, up, down]
		double[] runTypeTotals = new double[4];
		
		//creates runnable to run simulations
		for (int i = 0; i < NUM_RUNS / 4; i++) {
			//runnable for if the next move is left
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
			
			//runnable for if the next move is right
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
			
			//runnable for if the next move is up
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
			
			//runnable for if the next move is down
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
			
			//creates the threads from the runnables
			ExecutorService executor = Executors.newFixedThreadPool(4);
			
			Thread moveThread1 = new Thread(move1);
			Thread moveThread2 = new Thread(move2);
			Thread moveThread3 = new Thread(move3);
			Thread moveThread4 = new Thread(move4);
			
			//does parallel processing to run simulations
			executor.execute(moveThread1);
			executor.execute(moveThread2);
			executor.execute(moveThread3);
			executor.execute(moveThread4);
			
			executor.shutdown();	
		}
		
		//gets the average scores after each possible move
		double[] runAverages = { runTotals[0] / runTypeTotals[0],
								 runTotals[1] / runTypeTotals[1],
								 runTotals[2] / runTypeTotals[2],
								 runTotals[3] / runTypeTotals[3] }; 

		//gets which move had the highest average
		int index = 0;
		double max = runAverages[0];

		for (int i = 1; i < 4; i++) {
			if (runAverages[i] > max) {
				index = i;
				max = runAverages[i];
			}
		}

		//updates what the last move
		pastMoves[1] = pastMoves[0];
		pastMoves[0] = index;

		//Makes the best move and then returns the new board state
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

	/**
	 * Runs a randomized simulation from a given board
	 * @param tempBoard the start board
	 * @return the score after the game on the board has ended
	 */
	private int randomRun(Board tempBoard) {
		Random randGen = new Random();

		//does the random moves
		while (tempBoard.canMove()) {
			//generates the random move
			int move = randGen.nextInt(4);
			boolean change = false;
			
			//actually does the random move
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

			//checks if anything in the board changed
			//adds a new tile if something did change
			if (change) {
				tempBoard.addTile();
			}
		}

		//returns the end score after simulating
		return tempBoard.getScore();
	}
}
