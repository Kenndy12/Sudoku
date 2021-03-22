
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class sudoku_main {

public static void main(String [] args) {
		
		
		GUI newgui = new GUI();
		sudokuInterface testing1 = new sudokuInterface();
		listOfButtons thisButtons = new listOfButtons();
		
		thisButtons.getFinishButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				testing1.buttonClicked(e);
			}
		});
		thisButtons.addFinishButton();
		
		newgui.getContentPane().setBackground(new java.awt.Color(0));
		
		Dimension boxes = new Dimension(1280,600);
		Dimension buttonsSize = new Dimension(1280,100);
		
		newgui.setSize(1280,700);
		testing1.setPreferredSize(boxes);
		thisButtons.setPreferredSize(buttonsSize);
		
		newgui.add(testing1);
		newgui.add(thisButtons);
		newgui.setLocationRelativeTo(null);
		newgui.setVisible(true);
		newgui.setResizable(false);
		newgui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
}



class GUI extends JFrame {
	
	public GUI() {
		super("Sudoku");
		
		FlowLayout theFlow = new FlowLayout();
		theFlow.setVgap(0);
		
		setLayout(theFlow);
		
		setBackground(Color.BLACK);
		

		
	}
}

class sudokuInterface extends JPanel {
		
	private ArrayList <JTextField> theFirstRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theSecondRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theThirdRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theFourthRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theFifthRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theSixthRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theSeventhRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theEightRow = new ArrayList<JTextField>();
	private ArrayList <JTextField> theNinthRow = new ArrayList<JTextField>();
	
	private ArrayList <ArrayList<JTextField>> allTheRows = 
			new ArrayList<ArrayList<JTextField>>();
	
	private int[][] twoD_Representation = new int[9][9];
	private int[][] generatedBoard = new int[9][9];
	
	public sudokuInterface() {
		
		
		
		setLayout(new GridLayout(0,9));
		
		setBorder(null);
			
		firstRow(theFirstRow);
		secondRow(theSecondRow);
		thirdRow(theThirdRow);
		fourthRow(theFourthRow);
		fifthRow(theFifthRow);
		sixthRow(theSixthRow);
		seventhRow(theSeventhRow);
		eightRow(theEightRow);
		ninthRow(theNinthRow);
		
		
		allTheRows.add(theFirstRow);
		allTheRows.add(theSecondRow);
		allTheRows.add(theThirdRow);
		allTheRows.add(theFourthRow);
		allTheRows.add(theFifthRow);
		allTheRows.add(theSixthRow);
		allTheRows.add(theSeventhRow);
		allTheRows.add(theEightRow);
		allTheRows.add(theNinthRow);
		
		setTextFieldFormat(allTheRows);	
		fontForTextField(allTheRows);
		
		sudokuLogic sudokuGenerator = new sudokuLogic();
		generatedBoard = sudokuGenerator.getSudokuArray();
		displayHint(allTheRows,true);
		
		
		
	}
	
	/*Method to display hint on the sudoku board, roughly every cell has a
	55 percent chance to be shown as a hint */
	public void displayHint(ArrayList<ArrayList<JTextField>> theCells,boolean firstRound) {
		
		//Checks if this is the first round of the game
		if(!firstRound) {
			
			for(int i=0; i<theCells.size(); i++) {
				
				for(int j=0; j<theCells.get(i).size(); j++) {
					
					allTheRows.get(i).get(j).setText(null);
					allTheRows.get(i).get(j).setEditable(true);
					fontForTextField(allTheRows);
				}
			}
			
		}
		
		Random rand = new Random();
		
		for(int i=0; i<theCells.size(); i++) {
			
			for(int j=0; j<theCells.get(i).size(); j++) {
				
				int displayHintOrNot = rand.nextInt(100) + 1;
				
				if(displayHintOrNot <= 55) {
					
					theCells.get(i).get(j).setForeground(Color.BLUE);
					theCells.get(i).get(j).setText(String.valueOf(generatedBoard[i][j]));
					theCells.get(i).get(j).setEditable(false);
				}
				
			}
		}
		
		
	}
	
	//Method for handling event when "check" or "finish" is clicked
	public void buttonClicked(ActionEvent e) {
		
		JButton clicked = (JButton)e.getSource();
		if(clicked.getText().equalsIgnoreCase("Check")) {
			transformTo2DArray(allTheRows,clicked);
		}
		else {
			sudokuLogic sudokuGenerator = new sudokuLogic();
			generatedBoard = sudokuGenerator.getSudokuArray();
			displayHint(allTheRows,false);
			clicked.setText("Check");
		}
	}
	
	//Makes sure that only 1 char can be entered and it must be a number from 0-9
	public void setTextFieldFormat(ArrayList<ArrayList<JTextField>> a) {
		
		
		for(int i=0; i< a.size();i++) {
			
			for(int j=0;j<a.get(i).size();j++) {
				
				int temp = i;
				int temp2 =j;
				a.get(i).get(j).addKeyListener(new KeyAdapter() {
					
					public void keyTyped(KeyEvent e) { 
						
						if (a.get(temp).get(temp2).getText().length() >= 1 ) {
				            e.consume(); 
				        }
				        
				        if (!Character.isDigit(e.getKeyChar())) {
		                    e.consume();
		                }
					}
				});		
			}
		}	 
		
	}
	
	//Transform the cells into a 2D array for easier comparison
	public void transformTo2DArray(ArrayList<ArrayList<JTextField>> a,JButton button) {
		
		boolean emptyCell = false;
		
		for(int i=0; i<a.size(); i++) {

			for(int j=0; j<a.get(i).size(); j++) {
				
				//Checks if the cells are empty or not
				try {
				twoD_Representation[i][j] = Integer.parseInt(a.get(i).get(j).getText());
				}catch(NumberFormatException n) {
					JOptionPane.showInternalMessageDialog(null, "All Fields must be entered");
					emptyCell = true;
					break;
				}
			}
			if(emptyCell) {
				break;
			}
		}
		
		//If none of the cells are empty, start checking answer
		if(!emptyCell) {
			
			checkBoardAnswer(button);
		}
	}
	
	/*Compares the cells that has been transformed into a 2D array and the 2D answer board, if 
	all cells are correct changes button from "check" to "play again"*/
	public void checkBoardAnswer(JButton button) {
		
		boolean validOrNot = true;
		for(int i=0 ; i<allTheRows.size(); i++) {
			
			for(int j=0; j<allTheRows.get(i).size(); j++) {
				
				
				if(Integer.parseInt(allTheRows.get(i).get(j).getText()) != generatedBoard[i][j]) {
					
					validOrNot = false;
					allTheRows.get(i).get(j).setBackground(Color.RED);
				}
				else {
					allTheRows.get(i).get(j).setBackground(Color.BLACK);
				}
			}
		}
		if(validOrNot) {
			
			JOptionPane.showInternalMessageDialog(null, "Board Solved");
			button.setText("Play Again");
		}
		else if(!validOrNot) {
			
			JOptionPane.showInternalMessageDialog(null, "There are incorrect fields");
		}
	}
	
	public void firstRow(ArrayList<JTextField> firstRow1) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 0, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			firstRow1.add(testing);
			add(testing);
		}
		
		
	}
	
	public void secondRow(ArrayList<JTextField> secondRow2) {
			
			for(int i =0; i < 9 ; i++) {
				JTextField testing = new JTextField();
				testing.setBackground(Color.BLACK);
				
				if(i==2 || i==5) {
					Border textBorder = BorderFactory.createMatteBorder(1, 0, 0, 7, Color.WHITE);
					testing.setBorder(textBorder);
				}
				else {
					Border textBorder = BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE);
					testing.setBorder(textBorder);
				}
				secondRow2.add(testing);
				add(testing);
			}
	}
	
	public void thirdRow(ArrayList<JTextField> thirdRow3) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 7, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,7,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			thirdRow3.add(testing);
			add(testing);
		}
	}
	
	public void fourthRow(ArrayList<JTextField> fourthRow4) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 0, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			fourthRow4.add(testing);
			add(testing);
		}
	}
	
	public void fifthRow(ArrayList<JTextField> fifthRow5) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 0, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			fifthRow5.add(testing);
			add(testing);
		}
	}
	
	public void sixthRow(ArrayList<JTextField> sixthRow6) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 7, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,7,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			sixthRow6.add(testing);
			add(testing);
		}
	}
	
	public void seventhRow(ArrayList<JTextField> seventhRow7) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 0, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			seventhRow7.add(testing);
			add(testing);
		}
	}
	
	public void eightRow(ArrayList<JTextField> eightRow8) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 0, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			eightRow8.add(testing);
			add(testing);
		}
	}
	
	public void ninthRow(ArrayList<JTextField> ninthRow9) {
		
		for(int i =0; i < 9 ; i++) {
			JTextField testing = new JTextField();
			testing.setBackground(Color.BLACK);
			
			if(i==2 || i==5) {
				Border textBorder = BorderFactory.createMatteBorder(1, 0, 0, 7, Color.WHITE);
				testing.setBorder(textBorder);
			}
			else {
				Border textBorder = BorderFactory.createMatteBorder(1,0,0,1, Color.WHITE);
				testing.setBorder(textBorder);
			}
			ninthRow9.add(testing);
			add(testing);
		}
	}
	
	public void fontForTextField(ArrayList<ArrayList<JTextField>> fontsChanger) {
		
		Font fontTest = new Font("Comic Sans MS",Font.BOLD,40);
		
		for(int i=0; i < fontsChanger.size() ; i++) {
			
			for(int j=0; j < fontsChanger.get(i).size(); j++) {
				
				fontsChanger.get(i).get(j).setFont(fontTest);
				fontsChanger.get(i).get(j).setForeground(Color.WHITE);
				fontsChanger.get(i).get(j).setHorizontalAlignment(JTextField.CENTER);
			}
			
		}
	}
}

class listOfButtons extends JPanel {
	
	private JButton button;
	
	public listOfButtons() {
		
		button = new JButton("Check");
		setLayout(new FlowLayout());
		
		setBackground(Color.BLACK);
	}
	
	public JButton getFinishButton() {
		
		return button;
	}
	
	public void addFinishButton() {
		
		add(button);
	}
}
