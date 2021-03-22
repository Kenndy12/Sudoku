import java.util.ArrayList;
import java.util.Random;



public class sudokuLogic {

	
	public sudokuLogic() {
		
		
		buildSudoku();
			
	}
	private int[][] sudokuArray = {
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0}			
	};
	
	
	//Checks column validity (checks up to down) to see if it fits the sudoku rule
	public boolean checkColumnValidity(int number,int column) {
		
		
		for(int i=0;i<sudokuArray.length;i++) {
						
			if(sudokuArray[i][column] == number) {
				
				return false;
			}
		}
		
		return true;
		
	}
	
	//Checks row validity (checks left to right) to see if it fits the sudoku rule
	public boolean checkRowValidity(int number,int row) {
		
		for(int i=0;i<sudokuArray.length;i++) {
			
				
			if(sudokuArray[row][i] == number) {
					
				return false;
			}
		
		}
		
		return true;
	}
	
	//Checks if a box to see if its the sudoku rule
	public boolean checkBoxValidity(int number,int rowNum,int columnNum) {
		
		int startFromRow = rowNum - rowNum % 3;
		int startFromColumn = columnNum - columnNum % 3;
		
		for(int i=startFromRow;i<startFromRow+3;i++) {
			
			for(int j=startFromColumn;j<startFromColumn+3;j++) {
				
				int numberInCell = sudokuArray[i][j];
				
				if(number == numberInCell) {
					
					return false;
				}
			}
		}
		return true;
	}
	
	//Constructs a sudoku board
	public boolean buildSudoku() {
		
		int rowSize = 9;
		int colSize = 9;
		
		ArrayList<Integer> listOfNumbers = new ArrayList<Integer>();
		listOfNumbers.add(1);
		listOfNumbers.add(2);
		listOfNumbers.add(3);
		listOfNumbers.add(4);
		listOfNumbers.add(5);
		listOfNumbers.add(6);
		listOfNumbers.add(7);
		listOfNumbers.add(8);
		listOfNumbers.add(9);
		for(int row=0; row < rowSize; row++) {
			
			for(int col=0; col < colSize; col++) {
				
				if(sudokuArray[row][col] == 0) {
					
					for(int k=1 ; k<9; k++) {
						Random rand = new Random();
						int index = rand.nextInt(listOfNumbers.size());
						int number = listOfNumbers.get(index);
						listOfNumbers.remove(index);
						boolean validOrNot = checkColumnValidity(number,col) && checkRowValidity(number,row) &&
								checkBoxValidity(number,row,col);
						
						if(validOrNot) {
							sudokuArray[row][col] = number;
							
							if(buildSudoku()) {
								return true;
							}
							else {
								sudokuArray[row][col] = 0;
							}
						}
					}
					
					return false;
				}
			}
		}
		return true;
	}
	
	//Returns the valid sudoku board
	public int[][] getSudokuArray() {
		
		return sudokuArray;
	}
	
}
