package com.rmgroup.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CsvReader implements SudokuReader {

	/**
	 * The name of the file containing the Sudoku solution.
	 */
	final private Path file;
	
	/**
	 * The dimension of the square Sudoku grid. 
	 */
	final private Integer dimension;
	
	/**
	 * Constructs a CsvReader for the given {@code file} and given {@code dimension}.
	 * @param file 
	 * @param dimension
	 */
	public CsvReader(final Path file, final Integer dimension) {
       this.file = file;
       this.dimension = dimension; 
	}
	
	/**
	 * Returns a Square Grid of {@code dimension} consisting of the values from the 
	 * given Sudoku solution {@code file}.
	 * @return a SudoGrid containing legal values.
	 * @throws IOException if a problem occurs reading or accessing the file. 
	 */
	@Override
	public Integer[][] getGrid() throws IOException {
		
       Integer[][] sudokuGrid = new Integer[dimension][dimension];

       try (Stream<String> lines = Files.lines(file))  {

    	   AtomicInteger rowNumber = new AtomicInteger(0);

    	   // save the extracted row.
    	   lines.forEach((row) -> 
              sudokuGrid[rowNumber.getAndIncrement()] = extractCellValues(row, dimension)
           );
        } catch (IOException ioe) {
           throw new IOException(
              "Error: Problem occurred trying to read the file. " + ioe.getMessage());
        }
       return sudokuGrid;
     }
	
    /**
     * Extracts and validates values from the given line of data.
     * 
     * @param line to extract the values from.
     * @return a row of validated values within the specified range. 
     */
     protected Integer[] extractCellValues(final String line, final Integer dimension) {
	
        // collect the values 
        String[] rawCellValues = line.split(",");		

        if (dimension != rawCellValues.length) 
           throw new IllegalArgumentException(String.format(
              "ERROR: Mismatch between the dimension {%1$d} and number of elements in solution row {%2$d}.", dimension, rawCellValues.length));
        	
        // cell values need to satisfy Suduko grid dimensions.
        if (isInRange(rawCellValues, dimension)) {
        	return getCleansedRow(rawCellValues);
        } else 
        	throw new IllegalArgumentException("ERROR: Illegal raw cell values");
     }
     
     /**
      * Returns the raw cell values as to cleansed integer data.
      * Note: A call to isInRange() should be made before calling this method.
      *       Otherwise IllegalArgumentException or NumberFormatException may be thrown.
      * @param rawValues to be cleansed.
      * @return the integer data.
      */
     protected Integer[] getCleansedRow(final String[] rawValues) {
    	 
         IntStream intValues = Stream.of(rawValues)
                                     .mapToInt(rawValue -> Integer.parseInt(rawValue));

         Integer[] cleansedRow = intValues.boxed()
                                          .collect(Collectors.toList())
                                          .toArray(new Integer[0]);
         return cleansedRow;
     }

     
     /**
      * Checks whether all the row's elements are integer values within the required Sudoku range.
      *  
      * @param row to be checked
      * @param dimension of the grid
      * @throws NumberFormatException if any elements are non-integer.
      * @throws IllegalArgumentException if any elements are not within range.
      * @return {@code true} if all the elements are integer within range, {@code false} otherwise.
      */
     protected boolean isInRange(final String[] row, final Integer dimension) {
    		
         // First validate parameters
         if (row == null || row.length == 0)
             throw new IllegalArgumentException(
                     "ERROR: row parameter cannot be null or of Zero length.");
         
         // check for non ints?
         IntStream intStream;
         try {
            intStream = Stream.of(row).mapToInt(s -> Integer.parseInt(s));
         } catch (NumberFormatException nfe) {
        	 throw new NumberFormatException("Error: the grid contains non-integer values");
         }
         	 
         // Now check within all these values are satisfy the range requirements, 
         // i.e. a 9 x 9 grid contains values within range 1..9.         
         if (intStream.filter(v -> v > dimension || v < 1).count() > 0) {
            throw new IllegalArgumentException(
                    String.format("Error: Cell Value is outside range (%1$d,%2$d)", 1, dimension));			
         }         
         return true;
      }

}
