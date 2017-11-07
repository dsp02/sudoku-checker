package com.rmgroup.sudoku;

import java.io.IOException;

public interface SudokuReader {
	
	Integer[][] getGrid() throws IOException;
	
}
