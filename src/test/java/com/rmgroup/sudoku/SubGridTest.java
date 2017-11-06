package com.rmgroup.sudoku;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubGridTest {

	private Sudoku sudoku;
	
	@Before
	public void setUp() throws Exception {
		sudoku =  Sudoku.getInstance();
	}
	
	@After
	public void tearDown() throws Exception {
		sudoku = null;
	}
	
	Integer[][] testSolution = {
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9},
			{1,2,3,4,5,6,7,8,9}};
	
	
	@Test
	public void testTopLeftGrid() {
		Integer[][] expectedResult = {{1,2,3}, 	{1,2,3}, {1,2,3}};
		Integer[][] subGrid  = sudoku.subGrid(0, 0, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}

	@Test
	public void testTopMiddleGrid() {
		Integer[][] expectedResult = {{4,5,6}, {4,5,6}, {4,5,6}};
		Integer[][] subGrid  = sudoku.subGrid(0, 3, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}
	
	@Test
	public void testTopRightGrid() {		
		Integer[][] expectedResult = {{7,8,9}, {7,8,9}, {7,8,9}};		
		Integer[][] subGrid  = sudoku.subGrid(0, 6, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}
		
	@Test
	public void testCentreLeftGrid() {
		Integer[][] expectedResult = {{1,2,3}, 	{1,2,3}, {1,2,3}};
		Integer[][] subGrid  = sudoku.subGrid( 3, 0, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}

	@Test
	public void testCentreMiddleGrid() {
		Integer[][] expectedResult = {{4,5,6}, {4,5,6}, {4,5,6}};
		Integer[][] subGrid  = sudoku.subGrid(3, 3, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}
	
	@Test
	public void testCentreRightGrid() {		
		Integer[][] expectedResult = {{7,8,9}, {7,8,9}, {7,8,9}};		
		Integer[][] subGrid  = sudoku.subGrid(3, 6, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}
		
	@Test
	public void testBottomLeftGrid() {
		Integer[][] expectedResult = {{1,2,3}, 	{1,2,3}, {1,2,3}};
		Integer[][] subGrid  = sudoku.subGrid(6, 0, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}

	@Test
	public void testBottomMiddleGrid() {
		Integer[][] expectedResult = {{4,5,6}, {4,5,6}, {4,5,6}};
		Integer[][] subGrid  = sudoku.subGrid(6, 3, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}
		
	@Test
	public void testBottomRightGrid() {
		Integer[][] expectedResult = {{7,8,9}, 	{7,8,9}, {7,8,9}};		
		Integer[][] subGrid  = sudoku.subGrid(6, 6, 3, testSolution);
		
		assertTrue(Arrays.deepEquals(expectedResult, subGrid));
	}
	
	// Parameter Tests
	@Test(expected = IllegalArgumentException.class)
	public void testParentNull() {
		sudoku.subGrid(0, 0, 3, null);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testDimensionRowOutOfBounds() {
		sudoku.subGrid(7, 6, 3, testSolution);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testDimensionColOutOfBounds() {		
		sudoku.subGrid(6, 7, 3, testSolution);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDimensionNegative() {
		sudoku.subGrid(6, 7, -1, testSolution);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDimensionToLarge() {
		sudoku.subGrid(6, 7, testSolution[0].length + 1, testSolution);
	}
}
