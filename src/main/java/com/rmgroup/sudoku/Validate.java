package com.rmgroup.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.rmgroup.sudoku.Sudoku.Status;

public class Validate {
	
	/**
	 * The entry point to the application.
	 * @param args position 0 must contain the path of the file containing the solution.
	 */
	public static void main(String[] args) {

		Optional<Path> filePath = getFilePath(args);

		if (!filePath.isPresent()) 
			throw new IllegalArgumentException("ERROR: Input file not detected");

		// Assume solution is incorrect and determine otherwise.
		Status validity = Status.INVALID;
		
		// A Sudoku Grid of 9 x 9.
		int sudokuDimension = 9;
				
		try {
			// Read contents of the CSV file.
			SudokuReader csvReader = new CsvReader(filePath.get(), sudokuDimension);
			Integer[][] sudokuGrid = csvReader.getGrid();

			// inject & check.
			validity = Sudoku.getInstance().check(sudokuGrid);
		
		} catch (IllegalArgumentException | IndexOutOfBoundsException| IOException ex ) {
			System.out.println(ex.getMessage());
		}

		
		// Inform user of the result. 
		System.out.println("The solution: " + filePath.get() + " is " + validity.name());
	}
	
	/**
	 * Extracts the file path from the {@code paths} if it exists,
	 * otherwise returns an empty option.
	 * @param paths to get the file from.
	 * @return Option containing the file path, if it exists.
	 */
	private static Optional<Path> getFilePath(String[] paths) {
		if (paths.length > 0 && Files.exists(Paths.get(paths[0]))) 
			return Optional.of(Paths.get(paths[0]));
		else
			return Optional.empty();
	}	
}
