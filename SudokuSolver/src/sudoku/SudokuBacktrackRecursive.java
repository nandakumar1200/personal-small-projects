package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * recursive implementation of the sudoku solver.
 * 
 * @author Nanda Kumar J
 *
 */
public class SudokuBacktrackRecursive implements Sudoku {
	/*
	 * Private fields and helper methods if required---
	 */

	int [][] SudokuArr2d;
	static int guessCount;
	/*
	 * this is a constructor that makes the matrix.
	 */
	
	/**
	 * Creates a new puzzle by reading a file.
	 *
	 * @param filename Relative path of the file containing the puzzle in the given
	 *                 format
	 * @throws IOException 
	 * @requires The file must be 9 rows of 9 numbers separated by whitespace the
	 *           numbers should be 1-9 or 0 representing an empty square
	 */
	public SudokuBacktrackRecursive(String filename) throws IOException {
		BufferedReader file = new BufferedReader (new FileReader (filename));
		SudokuArr2d = new int[9][9];
		String line;
		for(int row = 0; row < SudokuArr2d.length; row++) {
			line = file.readLine();
			for(int col = 0; col < SudokuArr2d.length; col++) {
				SudokuArr2d[row][col] = Character.getNumericValue(line.charAt(col));	
			}
		}
		file.close();
	}

	/**
	 * Determines whether the given {@code number} can be placed in the given
	 * {@code row} without violating the rules of sudoku.
	 *
	 * @param row    which row to see if the number can go into
	 * @param number the number of interest
	 *
	 * @requires [{@code row} is a valid row index] and [{@code number} is a valid
	 *           digit]
	 * 
	 * @return true if it is possible to place that number in the row without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForRow(int row, int number) {
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";
		assert 0 <= row && row < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";

		for (int i = 0; i < 9; i++) {
			if (SudokuArr2d[row][i] == number) {
				return true;
			}
		}
		return false; 
	}

	/**
	 * Determines whether the given {@code number} can be placed in the given column
	 * without violating the rules of sudoku.
	 *
	 * @param col    which column to see if the number can go into
	 * @param number the number of interest
	 *
	 * @requires [{@code col} is a valid column index] and [{@code number} is a
	 *           valid digit]
	 * 
	 * @return true iff it is possible to place that number in the column without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForColumn(int col, int number) {
		assert 0 <= col && col < PUZZLE_HEIGHT_WIDTH : "Violation of valid column index";
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";

		for (int j = 0; j < 9; j++) {
			if (SudokuArr2d[j][col] == number) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines whether the given {@code number} can be placed in "box" starting
	 * at the given position without violating the rules of sudoku.
	 * 
	 * The positions marked # are the valid positions of start of a box. They are
	 * (0,0), (0,3), (0, 6), (3,0), (3,3), (3,6), (6,0), (6,3), (6,6).
	 * 
	 * <pre>
	 * #00|#00|#00|
	 * 000|000|000|
	 * 000|000|000|
	 * ---+---+---+
	 * #00|#00|#00|
	 * 000|000|000|
	 * 000|000|000|
	 * ---+---+---+
	 * #00|#00|#00|
	 * 000|000|000|
	 * 000|000|000|
	 * ---+---+---+
	 * </pre>
	 *
	 * @param boxStartRow row index at which the box of interest starts
	 * @param boxStartCol column index at which the box of interest starts
	 * @param number      the number of interest
	 *
	 * @requires [{@code boxStartRow} and {@code boxStartCol} are valid box start
	 *           indices] and [{@code number} is a valid digit]
	 *
	 * @return true if it is possible to place that number in the column without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForBox(int boxStartRow, int boxStartCol, int number) {
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";
		assert boxStartRow % BOX_HEIGHT_WIDTH == 0 : "Violation of valid boxStartRow";
		assert boxStartCol % BOX_HEIGHT_WIDTH == 0 : "Violation of valid boxStartCol";

		/*
		 * The reason we subtract and use modulus is to reach the valid starting 
		 * position of the start of a box.
		 * For Example if row = 2, then row = [2 - (2 % 3)] = 2 - 1 = 1. 
		 */
		int row = boxStartRow - boxStartRow % 3;   
		int col = boxStartCol - boxStartCol % 3;
		
		for(int i = row; i < row + 3; i++) {            //we add 3 to row and col because we want to stay											
			for(int j = col; j < col +3; j++) {			//within the smaller sub box.
				if(SudokuArr2d[row][col] == number) {
					return true;
				}
			}
		}
		return false; 
	}

	/**
	 * Determines whether the given {@code number} can be placed in the given
	 * position without violating the rules of sudoku.
	 *
	 * @param row    which row to see if the number can go into
	 * @param col    which column to see if the number can go into
	 * @param number the number of interest
	 *
	 * @requires [{@code row} is a valid row index] and [{@code col} is a valid
	 *           column index] and [{@code number} is a valid digit]
	 * 
	 * @return true iff it is possible to place that number in the column without
	 *         violating the rule of 1 unique number per row.
	 */
	public boolean isValidForPosition(int row, int col, int number) {
		assert 0 <= row && row < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= col && col < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 < number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";

		return !isValidForRow(row, number) && !isValidForColumn(col, number) && !isValidForBox(row/3*3, col/3*3, number);
	}

	/*
	 * Methods from sudoku interface implemented here
	 */
	
	/**
	 * Returns the element at the given position in the puzzle. If the element is
	 * not fixed yet, returns 0.
	 * 
	 * @param row row index of the element
	 * @param col column index of the element
	 * @return (row,col)-the element if fixed, 0 otherwise
	 * 
	 * @requires [{@code row} and {@code col} are valid indices]
	 */
	@Override
	public int element(int i, int j) {
		assert 0 <= i && i < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= j && j < PUZZLE_HEIGHT_WIDTH : "Violation of valid column index";

		if (SudokuArr2d[i][j] == 0) {
			return 0;
		}
		int a = SudokuArr2d[i][j];
		return a;
	}

	/**
	 * Sets the element at the given position to {@code number}.
	 * 
	 * @param row    row index of the element
	 * @param col    column index of the element
	 * @param number value to be set at the given position
	 * 
	 * @modifies {@code this}
	 * 
	 * @requires [{@code row} and {@code col} are valid indices] and [{@code number}
	 *           is a valid digit 0-9]
	 */
	@Override
	public void setElement(int i, int j, int number) {
		assert 0 <= i && i < PUZZLE_HEIGHT_WIDTH : "Violation of valid row index";
		assert 0 <= j && j < PUZZLE_HEIGHT_WIDTH : "Violation of valid column index";
		assert 0 <= number && number <= PUZZLE_HEIGHT_WIDTH : "Violation of valid cadidate";

		if(SudokuArr2d[i][j] == 0) {
			SudokuArr2d[i][j] = number;
		}
	}

	/**
	 * Tries to solve {@code this} puzzle, returns {@code true} if it is solved,
	 * {@code false} otherwise.
	 * 
	 * @return true if this puzzle is solved
	 * 
	 * @modifies {@code this}
	 */
	@Override
	public boolean solve() {
		
		for (int r = 0; r < PUZZLE_HEIGHT_WIDTH; r++) {
			for (int c = 0; c < PUZZLE_HEIGHT_WIDTH; c++) {
				if (SudokuArr2d[r][c] == ZERO) {
					for (int randNum = 1; randNum <= PUZZLE_HEIGHT_WIDTH; randNum++) {
						if (isValidForPosition(r, c, randNum)) {
							SudokuArr2d[r][c] = randNum;
							if (solve()) {
								guessCount++;
								return true;
							} else {
								SudokuArr2d[r][c] = ZERO;
							}
						}
					}
					return false;
				}
			}
		}

		return true;
	}
	
	public int getGuessCount() {
		return guessCount;
	}
	/**
	 * Verifies whether {@code this} puzzle is solved.
	 * 
	 * @return true if this puzzle is solved
	 */
	@Override
	public boolean verify() {
		
		for(int row = 0; row < SudokuArr2d.length; row++) {
			for(int col = 0; col < SudokuArr2d.length; col++)
			{
				if(isValidForRow(row,element(row, col)) && isValidForColumn(col,element(row,col)) && isValidForBox(row/3*3, col/3*3,element(row, col))) {
					return true;
				}
			}
		}
		return false; 
	}

	/*
	 * Methods inherited from Object
	 */
	@Override
	public String toString() {
		String s = "";
		for (int row = 0; row < SudokuArr2d.length; row++) {
			for (int col = 0; col < SudokuArr2d.length; col++) {
				if(SudokuArr2d[row][col] == 0) {
					s = s + " " + "|";
				}
				else {
					s = s + SudokuArr2d[row][col] + "|";
				}
			}
			s = s + '\n';
			if (row == 2 || row == 5 || row == 8) {
				s = s + "-----+-----+-----+" + '\n';
			}
		}
		return s;
	}
	

	/**
	 * Main method. Produces the following output. You can modify it to debug and
	 * refine your implementation.
	 * 
	 * <pre>
	===== Sudoku puzzle =====
	
   | |3| |2| |6| | |
  9| | |3| |5| | |1|
   | |1|8| |6|4| | |
  -----+-----+-----+
   | |8|1| |2|9| | |
  7| | | | | | | |8|
   | |6|7| |8|2| | |
  -----+-----+-----+
   | |2|6| |9|5| | |
  8| | |2| |3| | |9|
   | |5| |1| |3| | |
  -----+-----+-----+
	
	
	
	===== Solving sudoku =====
	
	
	4|8|3|9|2|1|6|5|7|
	9|6|7|3|4|5|8|2|1|
	2|5|1|8|7|6|4|9|3|
	-----+-----+-----+
	5|4|8|1|3|2|9|7|6|
	7|2|9|5|6|4|1|3|8|
	1|3|6|7|9|8|2|4|5|
	-----+-----+-----+
	3|7|2|6|8|9|5|1|4|
	8|1|4|2|5|3|7|6|9|
	6|9|5|4|1|7|3|8|2|
	-----+-----+-----+
	 * </pre>
	 * 
	 * @param args command line arguments, not used
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Sudoku s = new SudokuBacktrackRecursive("/Users/nandu/eclipse-workspace/SudokuSolver/src/sudoku/sudoku1.txt");

		System.out.println("===== Sudoku puzzle =====\n");
		System.out.println(s.toString());

		System.out.println("\n\n===== Solving sudoku =====\n\n");
		s.solve();
		System.out.println(s);
		
		long startTime = System.nanoTime();
		s.solve();
		long endTime = System.nanoTime();
		System.out.println("no of guesses: " + guessCount);
		System.out.println("Time Taken: " + (endTime - startTime));
	}
}
