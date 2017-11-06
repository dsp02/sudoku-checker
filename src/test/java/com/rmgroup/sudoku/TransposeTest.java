package com.rmgroup.sudoku;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransposeTest {
	
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
	public void transposeSubGridTest() {
		Integer[][] original = {{1,2,3}, {4,5,6}, {7,8,9}};
		Integer[][] expected = {{1,4,7}, {2,5,8}, {3,6,9}};		
		Integer[][] transposed = sudoku.transpose(original);
		
		assert(Arrays.deepEquals(expected, transposed));		
	}
	
	@Test
	public void transposeFullGridTest() {
		
		Integer[][] original = {{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9},
								{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9},
								{1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9}, {1,2,3,4,5,6,7,8,9}};
		
		Integer[][] expected = {{1,1,1,1,1,1,1,1,1}, {2,2,2,2,2,2,2,2,2}, {3,3,3,3,3,3,3,3,3},
				                {4,4,4,4,4,4,4,4,4}, {5,5,5,5,5,5,5,5,5}, {6,6,6,6,6,6,6,6,6},
				                {7,7,7,7,7,7,7,7,7}, {8,8,8,8,8,8,8,8,8}, {9,9,9,9,9,9,9,9,9}};		
		
		Integer[][] transposed = sudoku.transpose(original);
		
		assert(Arrays.deepEquals(expected, transposed));		
	}

	@Test(expected = IllegalArgumentException.class)
	public void nullTransposeTest() {
		sudoku.transpose(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void transposeInvalidDimensionsGridTest() {		
		Integer[][] original = {{1,2,3}, {4,5}, {7}};
		sudoku.transpose(original);
	}
	
}
