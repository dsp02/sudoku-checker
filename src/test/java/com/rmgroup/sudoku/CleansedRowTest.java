package com.rmgroup.sudoku;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import edu.emory.mathcs.backport.java.util.Arrays;

public class CleansedRowTest {

    // Only a quick smoke test here. 
    // minimal test as should be called after isInRanger();
	
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
	public void test() {
		
		String[] rawValues = {"1", "2", "3"};
		Integer[] expected = {1, 2, 3};		
		
		assertTrue(Arrays.deepEquals(expected, reader.getCleansedRow(rawValues)));
	}
	
	

}
