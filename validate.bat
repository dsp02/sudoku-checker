
@echo off

if [%1]==[] goto :cmdLineExample

SET JAR_FILE="target/sudoku-validator-0.0.1.jar"
SET SOLUTION=%1 

java -jar %JAR_FILE% %SOLUTION%

@echo Finished.
goto :end


:cmdLineExample
@echo Error: missing solution file parameter. 
@echo Example usage: validate target/test-classes/sudokuValid.csv   
exit /B 1

:end