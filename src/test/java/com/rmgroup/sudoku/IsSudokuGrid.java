package com.rmgroup.sudoku;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IsSudokuGrid {

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
	public void validOrderedGridTest() {
		Integer[][] testGrid = {{1,2,3},{4,5,6},{7,8,9}};
		assertTrue(sudoku.isSudokuGrid(testGrid));
	}
	
	@Test
	public void validUnorderedGridTest() {
		Integer[][] testGrid = {{5,7,1},{8,3,6},{9,2,4}};
		assertTrue(sudoku.isSudokuGrid(testGrid));
	}
	
	@Test
	public void doubleValueGridTest() {
		Integer[][] testGrid = {{5,7,1},{8,5,6},{9,2,4}};
		assertFalse(sudoku.isSudokuGrid(testGrid));
	}
	
	@Test
	public void outOfRangeValuesGridTest() {
		Integer[][] testGrid = {{5,7,1},{18,5,6},{9,2,4}};
		assertFalse(sudoku.isSudokuGrid(testGrid));
	}
	
	@Test
	public void tooManyValuesGridTest() {
		Integer[][] testGrid = {{5,7,1},{8,5,6,1},{9,2,4}};
		assertFalse(sudoku.isSudokuGrid(testGrid));
	}
	
	@Test
	public void emptyGridTest() {
		Integer[][] testGrid = {{},{},{}};
		assertFalse(sudoku.isSudokuGrid(testGrid));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullGridTest() {
		assertFalse(sudoku.isSudokuGrid(null));
	}

}
