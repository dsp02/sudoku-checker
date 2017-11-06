package com.rmgroup.sudoku;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.rmgroup.sudoku.Sudoku.SolutionValidity;

public class Validate {
	
	/**
	 * The entry point to the application.
	 * @param args position 0 must contain the path of the file containing the solution.
	 */
	public static void main(String[] args) {

		Optional<Path> filePath = getFilePath(args);

		if (!filePath.isPresent()) 
			throw new IllegalArgumentException("ERROR: Input file not detected");
		
		// Now check it.
		Sudoku sudoku = Sudoku.getInstance();
		SolutionValidity validity = SolutionValidity.INVALID;
		
		try {
			validity = sudoku.check(filePath.get());
		} catch (IllegalArgumentException | IndexOutOfBoundsException ex ) {
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
