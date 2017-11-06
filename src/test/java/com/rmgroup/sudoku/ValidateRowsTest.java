package com.rmgroup.sudoku;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ValidateRowsTest {

private Sudoku sudoku;
	
	@Before
	public void setUp() throws Exception {
		sudoku =  Sudoku.getInstance();
	}
	
	@After
	public void tearDown() throws Exception {
		sudoku = null;
	}
	
	@Test
	public void validRowTest() {
		String[] row = {"1","2","3","4","5","6","7","8","9"};		
		assertTrue(sudoku.isWithinRange(row, 1, 9));
	}
	
	@Test
	public void validMixedRowTest() {
		String[] row = {"9","2","8","4","7","6","1","3","5"};		
		assertTrue(sudoku.isWithinRange(row, 1, 9));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidTooBigRowTest() {
		String[] row = {"9","1","8","4","7","6","10","3","5"};		
		sudoku.isWithinRange(row, 1, 9);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidTest() {
		String[] row = {"1","8","4","7","6","10","3","5"};		
		sudoku.isWithinRange(row, 1, 9);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidTooSmallRowTest() {
		String[] row = {"9","1","8","4","7","6","0","3","5"};		
		sudoku.isWithinRange(row, 1, 9);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidLetterRowTest() {
		String[] row = {"9","1","X","4","7","6","0","3","5"};		
		sudoku.isWithinRange(row, 1, 9);
	}
	
	@Test
	public void emptyRowTest() {
		String[] row = {};	
		sudoku.isWithinRange(row, 1, 9);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullRowTest() {
		sudoku.isWithinRange(null, 1, 9);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidRangeRowTest() {
		String[] row = {"9","1","2","4","7","6","0","3","5"};
		sudoku.isWithinRange(row, 9, 1);
	}
	
}
