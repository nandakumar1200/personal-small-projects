package sudoku;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test cases for public methods of the {@code SudokuBacktrackRecursive}
 * class.
 * 
 * @author Nanda Kumar J
 *
 */
public class SudokuBacktrackRecursiveTest {
	private SudokuBacktrackRecursive s;

	@Before // executed before every test case
	public void setUp() 
	throws IOException{
		s = new SudokuBacktrackRecursive("/Users/nandu/eclipse-workspace/SudokuSolver/src/sudoku/sudoku1.txt");
	}

	@Test
	public void number4IsValidAtRow0Col0() {
		s.solve();
		final int number = 4;
		assertEquals(true, s.isValidForRow(0, number));
		assertEquals(true, s.isValidForColumn(0, number));
		assertEquals(true, s.isValidForBox(0, 0, number));
	}
	
	@Test
	public void testToString() {
		assertEquals(
				" | |3| |2| |6| | |\n" + //
				"9| | |3| |5| | |1|\n" + //
				" | |1|8| |6|4| | |\n" + //
				"-----+-----+-----+\n" + //
				" | |8|1| |2|9| | |\n" + //
				"7| | | | | | | |8|\n" + //
				" | |6|7| |8|2| | |\n" + //
				"-----+-----+-----+\n" + //
				" | |2|6| |9|5| | |\n" + //
				"8| | |2| |3| | |9|\n" + //
				" | |5| |1| |3| | |\n" + //
				"-----+-----+-----+\n", s.toString());
	}

	@Test
	public void testForRow() {
		final int number = 8;
		s.solve();
		assertEquals(true, s.isValidForRow(2, number));
	}
	
	@Test
	public void testForRow2() {
		final int number = 4;
		s.solve();
		assertEquals(true, s.isValidForRow(0, number));
	}
	
	@Test
	public void testForCol() {
		final int number = 4;
		s.solve();
		assertEquals(true, s.isValidForColumn(0, number));
	}
	
	@Test
	public void testForCol2() {
		final int number = 1;
		s.solve();
		assertEquals(true, s.isValidForColumn(5, number));
	}
	
	@Test
	public void testForBox() {
		final int number = 4;
		s.solve();
		assertEquals(true, s.isValidForBox(0, 0, number));
	}
	
	@Test
	public void number5IsValidAtRow4Col0() {
		final int number = 5;
		s.solve();
		assertEquals(true, s.isValidForRow(4, number));
		assertEquals(true, s.isValidForColumn(0, number));
		assertEquals(true, s.isValidForBox(3, 0, number));
	}
	
	@Test
	public void testForVerify() {
		s.solve();
		assertEquals(true, s.verify());
	}
}
