package com.rmgroup.sudoku;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;

public class ExtractCellValuesTest {

	private Path filePath; 
	private Integer dimension;
    private CsvReader reader;

	@Before
	public void setUp() throws Exception {
		filePath = Paths.get("DummyFile");
		dimension = 9;
		reader = new CsvReader(filePath, dimension);
	}

	@Test
	public void testExtractCellValues() {
		Integer[] expected = {1,2,3,4,5,6,7,8,9}; 
		String line = "1,2,3,4,5,6,7,8,9";
		
		assertTrue(Arrays.deepEquals(expected, reader.extractCellValues(line, dimension)));
	}
	
	@Test
	public void testExtractSmallCellValues() {
		Integer[] expected = {1,2,3}; 
		String line = "1,2,3";
		
		assertTrue(Arrays.deepEquals(expected, reader.extractCellValues(line, 3)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNonIntegerValue() {
		String line = "1,2,3,4,5,6,£,8,9";
		reader.extractCellValues(line, 9);		
	}
		
	@Test(expected = IllegalArgumentException.class)
	public void testOutOfRangeValueSmall() {
		String line = "1,2,3,4,5,6,0,8,9";
		reader.extractCellValues(line, 9);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOutOfRangeValueBig() {
		String line = "1,2,3,4,5,6,10,8,9";
		reader.extractCellValues(line, 9);		
	}
	@Test(expected = IllegalArgumentException.class)
	public void testCellValueLengthGreaterThanDimension() {
		String line = "1,2,3,4,5,6,7,8,9,9";
		reader.extractCellValues(line, 9);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDimensionGreaterThanCellValuesLength() {
		String line = "1,2,3,4,5,6,7,8,9";
		reader.extractCellValues(line, 10);
	}

}
