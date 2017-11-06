package com.rmgroup.sudoku;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IsSudokuLine {

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
	public void validLineTest() {		
		Integer[] line = {1,2,3,4,5,6,7,8,9};
		assertTrue(sudoku.isSudokuLine(line));
	}
	
	@Test
	public void mixedLineTest() {
		Integer[] line = {9,8,1,2,3,7,5,6,4};
		assertTrue(sudoku.isSudokuLine(line));
	}
	
	@Test
	public void doubleElementsTest() {
		Integer[] line = {1,2,3,4,5,6,7,3,9};
		assertFalse(sudoku.isSudokuLine(line));
	}
	
	@Test
	public void missingElementsTest() {
		Integer[] line = {1,2,3,4,5,6,7,9};
		assertFalse(sudoku.isSudokuLine(line));
	}
	
	@Test
	public void tooManyElementsTest() {
		Integer[] line = {1,2,3,4,5,6,7,9,10};
		assertFalse(sudoku.isSudokuLine(line));
	}
	
	@Test
	public void emptyElementsTest() {
		Integer[] line = {};
		assertFalse(sudoku.isSudokuLine(line));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullElementsTest() {
		Integer[] line = null;
		assertFalse(sudoku.isSudokuLine(line));
	}

}
