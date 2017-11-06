package com.rmgroup.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sudoku {

    /**
     *  The possible outcomes of checking a solution. 
     */
    public enum Status {VALID, INVALID}

    /**
     * The set of values which constitute a valid Sudoku row, column or subgrid for a 9 x 9 
     * dimension solution. 
     */
	private static final Set<Integer> validValues = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

    /**
     * The dimension for a 9 x 9 solution.
     */
     private static final int GRID_DIMENSION = 9;

    /**
     * The size of all subgrids is 3 x 3 for a 9 x 9 solution.
     */
     private static final int SUBGRID_DIMENSION = 3;

    /**
     * Returns an instance of a Sudoku.
	 * @return a Sudoku.
     */
    public static Sudoku getInstance() {
       return new Sudoku();
    }

    /**
     * Checks whether the Sudoku Solution and provides an answer as to whether it is 
     * a valid solution or an invalid solution according to Sudoku rules.
     *  
     * @return {@code VALID} if it is a Sudoku solution, {@code INVALID} otherwise. 
     */
    public Status check(final Path sudokuFilePath) {
	
       Status result = Status.VALID;

       Integer[][] sudokuGrid = getSudokuValues(sudokuFilePath).get();

       // Now perform Sudoku checks
       result = checkAllRows(sudokuGrid);
       result = checkAllColumns(result, sudokuGrid);
       result = checkAllSubGrids(result, sudokuGrid, SUBGRID_DIMENSION);

       return result;
    }

    /**
     * Checks whether all columns in the grid are valid according to Sudoku rules.
     * @param result holds the current status of previous checks. 
     * @param grid to check.
     * @return {@code Valid} if all rows are valid, {@code INVALID} otherwise.
     */
    private Status checkAllColumns(final Status result, final Integer[][] grid) {
    
       Status currentResult = result;
   
       // transpose the grid then we can just check rows!
       if (result.equals(Status.VALID)) {
          Integer[][] columnView = transpose(grid);
          currentResult = checkAllRows(columnView);
       }
       return currentResult;
    }
    
    
    /**
     * Checks whether all rows in the grid are valid according to Sudoku rules. 
     * @param grid to check.
     * @return {@code Valid} if all rows are valid, {@code INVALID} otherwise.
     */
     private Status checkAllRows(final Integer[][] grid) {
	
        Status answer = Status.VALID;

        for(int row = 0; row < grid.length; row++) {
           if (answer.equals(Status.VALID) && !isSudokuLine(grid[row])) {
              answer = Status.INVALID;	
           }
        }
        return answer;
     }


    /**
     * Checks whether all subgrids in the parent grid are valid according to Sudoku rules. 
     * @param src the parent grid
     * @param dimension of the subgrid
     * @return {@code VALID} if all subgrids satisfy Sudoku rules, {@code INVALID} otherwise.
     */
     protected Status checkAllSubGrids(
        final Status result, final Integer[][] src, final int dimension) {
        
    	Status currentResult = result;

        for (int row = 0; row < src.length; row += dimension) {
           
        	for (int col = 0; col < src.length; col += dimension) {
        	   Integer[][] grid = subGrid(row, col, dimension, src);
              
              if (currentResult.equals(Status.VALID) && !isSudokuGrid(grid)) {
                 currentResult = Status.INVALID;
              }
           }
        }
        return currentResult;
     }

	
    /**
     * Copies the specified range of the specified array into a new array.
     * @param fromRow the initial row of the range to be copied, inclusive
     * @param fromCol the initial column of the range to be copied, inclusive
     * @param dimension the length to be copied. 
     * @param parent the array from which a range is to be copied.
     * @throws ArrayIndexOutOfBoundsException if {@dimension from &lt 0 or &gt parent.length[0]}
     *         or {@dimension + fromRow (or fromCol) > original.length}
     * @throws IllegalArgumentException if <tt>fromRow or fromCol &gt parent lengths; to</tt>
     * @throws NullPointerException if <tt>parent</tt> is null
     * @return the subgrid containing the copied elements.
     */
	protected Integer[][] subGrid(final Integer fromRow, 
       final Integer fromCol, final Integer dimension, final Integer[][] parent) {
	
		gridRangeCheck(fromRow, fromCol, dimension, parent);
		
		Integer[][] subGrid = new Integer[dimension][dimension];
		
		int newRow = 0;
		int newCol = 0;
		
		for(int row = fromRow; row < fromRow + dimension; row++) {
			for(int col = fromCol; col < fromCol + dimension ; col++) {
				subGrid[newRow][newCol] = parent[row][col];
				newCol++;
			}
			// reset to next row.
			newCol = 0;
			newRow++;
		}
		return subGrid;
	}

	
    /**
     * Checks that {@code fromRow}, {@code fromCol} and {@code dimension} are in
     * the range, throws an exception if they are not.
     */
     private void gridRangeCheck(final Integer fromRow, 
        final Integer fromCol, final Integer dimension, final Integer[][] parent) {
        
        if (parent == null) {
           throw new IllegalArgumentException("ERROR: parent grid cannot be null");
        }
        if (fromRow > parent[0].length) {
           throw new IllegalArgumentException(
              String.format("ERROR: fromRow(%1$d) > %2$d", fromRow, parent.length));
        }
        if (fromCol > parent[1].length) {
           throw new IllegalArgumentException(
              String.format("ERROR: fromCol(%1$d) > %2$d", fromCol, parent[1].length));
        }
        if (fromRow < 0) {
           throw new ArrayIndexOutOfBoundsException(
              String.format("ERROR: %1$d < $2%d", fromRow, 0));
        }
        if (fromCol < 0) {
           throw new ArrayIndexOutOfBoundsException(
              String.format("ERROR: %1$d < %2$d", fromCol, 0));
        }
        if (dimension < 0 || dimension > parent[0].length) {
           throw new IllegalArgumentException(
              String.format("ERROR: dimension (%1$d) is outside range (%2$d,%3$d)", dimension, 0, parent[0].length));
        }	
        if (fromRow + dimension > parent[0].length) {
           throw new ArrayIndexOutOfBoundsException(
              String.format("ERROR: %1$d + %2$d > %3$d", fromRow, dimension, parent[0].length));
        }
        if (fromCol + dimension > parent[1].length) {
           throw new ArrayIndexOutOfBoundsException(
              String.format("ERROR: %1$d + %2$d > %3$d", fromCol, dimension, parent[1].length));
        }		
     }
       
     
    /**
     * Validates the given row's elements lie within min .. max range. 
     * @param row to be validated
     * @param Max value an element can take in the row.
     * @param Min value an element can take in the row.
     * @return true if all elements in the row are valid, false otherwise.
     */
     protected boolean isWithinRange(final String[] row, final int min, final int max) {
	
        // First validate parameters
        if (row == null) 
           throw new IllegalArgumentException("ERROR: row parameter cannot be null");
        
        if (min > max) throw new IllegalArgumentException(
           String.format("ERROR: Min %1$d cannot be greater than Max %2$d", min, max));

        // now check values are within range.
        IntStream intStream = Stream.of(row).mapToInt(s -> Integer.parseInt(s));
		
        if (intStream.filter(v -> v > max || v < min).count() > 0) {
           throw new IllegalArgumentException(
              String.format("Error: Cell Value is outside range (%1$d,%2$d)", min, max));			
        }		
        return true;
     }

     
    /**
     * Transposes the {@code src} square matrix, i.e. turns rows into columns.
     * @param src matrix to be transposed.
     * @throws IllegalArgumentException if the src is empty or the row length 
     *         is not equal to column length.
     * @return the transposed matrix.
     */
     protected Integer[][] transpose(final Integer[][] src) {	
	
        if (src == null) 
           throw new IllegalArgumentException("ERROR: src grid cannot be null");

        if (src[1].length != src[2].length) 
           throw new IllegalArgumentException("ERROR: src grid must be square in dimensions");

        Integer[][] columnView = new Integer[src[0].length][src[1].length];

        for (int row = 0; row < src[0].length; row++) {
           for (int col = 0; col < src[1].length; col++) {
              columnView[row][col] = src[col][row];
           }
        }
        return columnView;
     }

    /**
     * Checks whether the given line is a valid Sudoku line, that is, contains all values
     * from in the range 1-9 and each is unique.
     * @param line to validate.
     * @return true if the line is a valid Sudoku line, false otherwise.
     */
     protected boolean isSudokuLine(final Integer[] line) {
 	
     if (line == null) 
        throw new IllegalArgumentException("ERROR: a Sudoku line cannot be null.");
		
        Set<Integer> row = new HashSet<Integer>(Arrays.asList(line));
        
        return validValues.equals(row);
     }

    /**
     * Checks whether the given grid is a valid Sudoku grid, that is, contains all values
     * from in the range 1-9 and each is unique.
     * @param grid to validate.
     * @return true if the line is a valid Sudoku grid, false otherwise.
     */
     protected boolean isSudokuGrid(final Integer[][] grid) {
	
       if (grid == null) 
          throw new IllegalArgumentException("ERROR: the grid cannot be null");

       // flatten the elements into a set for comparison.
       List<Integer> list = Arrays.stream(grid)
                                  .flatMap(Arrays::stream)
                                  .collect(Collectors.toList());

       Set<Integer> allElements = new HashSet<Integer>(list);

       return validValues.equals(allElements);
    }


    /**
     * A utility method for displaying the contents of the grid to the console. 
     * @param grid
     */
     public static void print(final Integer[][] grid) {
        for (int row = 0; row < grid[0].length; row++) {
           for (int col = 0; col < grid[1].length; col++) {
              System.out.print("["+ row +"][" + col +"] = " +   grid[row][col] + ",  ");
           }
           System.out.println("\n");
        }
     }
	
    /**
     * Extracts the Sudoku values from the provided solution file.
     * @param file 
     * @return A grid containing the values.
     */
     protected Optional<Integer[][]> getSudokuValues(final Path file) {	
        Integer[][] sudokuValues = new Integer[GRID_DIMENSION][GRID_DIMENSION];

        try (Stream<String> lines = Files.lines(file))  {
           AtomicInteger rowNumber = new AtomicInteger(0);
           lines.forEach((row) -> 
              sudokuValues[rowNumber.getAndIncrement()] = extractCellValues(row)
           );
        } catch (IOException ioe) {
           return Optional.empty();
        }

        return Optional.of(sudokuValues);
     }

    /**
     * Extracts and validates values from the given line of data.
     * @param line to extract the values from.
     * @return a row of validated values within the specified range. 
     */
     private Integer[] extractCellValues(final String line) {
	
        String[] cellValues = line.split(",");		

        if (!isWithinRange(cellValues, 1, GRID_DIMENSION)) 
           throw new IllegalArgumentException("ERROR: cell values are not in range");
	
        IntStream intValues = Stream.of(cellValues)
                                    .mapToInt(rawValue -> Integer.parseInt(rawValue));

        Integer[] row = intValues.boxed()
                                 .collect(Collectors.toList())
                                 .toArray(new Integer[0]);
        return row;
     }
}