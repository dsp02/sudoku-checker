package com.rmgroup.sudoku;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class IsInRangeTest {

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
	public void testIsInRange() {
		String[] vals = {"1","2","3"}; 
		assertTrue(reader.isInRange(vals, dimension));
	}
		
	@Test
	public void allDimensionValuesTest() {
		String[] vals = {"1","2","3","4","5","6","7","8","9"};		
		assertTrue(reader.isInRange(vals, dimension));
	}
	
	@Test
	public void validMixedValuesTest() {
		String[] vals = {"9","2","8","4","7","6","1","3","5"};		
		assertTrue(reader.isInRange(vals, dimension));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void outOfRangeTooBigTest() {
		String[] vals = {"9","1","8","4","7","6","10","3","5"};		
		reader.isInRange(vals, dimension);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void outOfRangeTooSmallTest() {
		String[] vals = {"9","1","8","4","7","6","0","3","5"};		
		reader.isInRange(vals, dimension);
	}
		
	@Test(expected = IllegalArgumentException.class)
	public void invalidCharTest() {
		String[] vals = {"9","1","X","4","7","6","0","3","5"};		
		reader.isInRange(vals, dimension);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void emptyRowTest() {
		String[] vals = {};	
		reader.isInRange(vals, dimension);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nullRowTest() {
		reader.isInRange(null, dimension);
	}
	

}
