package com.rmgroup.sudoku;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	IsSudokuLine.class,
	IsSudokuGrid.class,
	ValidateRowsTest.class,
	SubGridTest.class,
	TransposeTest.class
})
public class AllTests {
}
