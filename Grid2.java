import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Grid2 implements ActionListener  { //MineSweepeer Game


	// Layout Manager = Defines the natural layout for components within a container

	// GridLayout = 	places components in a grid of cells. 
	//					Each component takes all the available space within its cell, 
	//					and each cell is the same size. 
	private boolean [ ][ ]bombGrid; 
	private int [ ][ ] countGrid; 
	private int numRows; 
	private int numColumns; 
	private int numBombs;
	private JFrame frame; 
	private JButton [][]buttons;
	public Grid2() { 							//10 rows, 10 columns, (10 x 10 Grid), 25 bombs


		bombGrid = new boolean [10][10]; 	
		countGrid = new int [10][10];	
		numRows = 10;
		numColumns = 10;
		numBombs = 25;
		frame = new JFrame("MineSwepper"); // if JFrame extends the class we dont need to create an Jframe object from the Jframe class since the class inherits all the method
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to create an instance of  a class means to create an object of that specific class 
	    frame.setSize(450,450);
		frame.setLocationRelativeTo(null); 
		frame.setLayout(new GridLayout(numRows,numColumns));

		createBombGrid();
		createCountGrid();


		frame.setVisible(true);
		showAllBombs();
		System.out.println();
		showGridCount();

	}	 
	public Grid2(int rows, int columns) { 				//(rows x columns Grid), default value of 25 bombs 

		this.numRows = rows;
		this.numColumns = columns;
		bombGrid = new boolean [numRows][numColumns]; 	
		countGrid = new int [numRows][numColumns];	
		numBombs = 25;

		frame = new JFrame("MineSwepper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450,450);
		frame.setLocationRelativeTo(null); 
		frame.setLayout(new GridLayout(numRows,numColumns));

		createBombGrid();
		createCountGrid();
		frame.setVisible(true);
		showAllBombs();
		System.out.println();
		showGridCount();
	}

	public Grid2(int rows, int columns, int numBombs) { 		//(rows x columns Grid), numBombs as specified

		this.numRows = rows;
		this.numColumns = columns;
		bombGrid = new boolean [numRows][numColumns]; 	
		countGrid = new int [numRows][numColumns];	
		this.numBombs = numBombs;
		frame = new JFrame("MineSwepper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(450,450);
		frame.setLocationRelativeTo(null); 
		frame.setLayout(new GridLayout(this.numRows,this.numColumns));
		createBombGrid();
		createCountGrid();


		frame.setVisible(true);
		showAllBombs();
		System.out.println();
		showGridCount();
	}	
	public int	getNumRows() {

		return numRows;
	}
	public int getNumColumns() {

		return numColumns;
	}
	public int getNumBombs() {

		return numBombs;
	}
	public boolean [ ][ ]  getBombGrid(){
		boolean [][] copy = new boolean[numRows][numColumns];
		for (int i = 0; i < copy.length; i++){
			for (int j = 0; j < copy[i].length; j++){
				copy [i][j] = bombGrid[i][j];

			}
		}
		return copy;
	}
	public int [][] getCountGrid(){ 

		int [][] copy = new int[numRows][numColumns];
		for (int i = 0; i < copy.length; i++){
			for (int j = 0; j < copy[i].length; j++){
				copy [i][j] = countGrid[i][j];

			}
		}
		return copy;
	}
	public boolean	isBombAtLocation(int row, int column) {


		return bombGrid[row][column];


	}
	public int 	getCountAtLocation(int row, int column) {
		return countGrid[row][column];

	}
	private void createBombGrid() {
		Random random = new Random();
		buttons = new JButton[numRows][numColumns]; //create 2 dimensional array of buttons that stores buttons


		for (int i = 0; i < bombGrid.length; i++){
			for (int j = 0; j < bombGrid[i].length; j++){
				bombGrid[i][j] = false;
			}       
		}
		

		int bombsAdded = 0; 
		while (bombsAdded < numBombs) {
			int x = random.nextInt(numRows);
			int y = random.nextInt(numColumns); 
			if (bombGrid[x][y] == false) {

				buttons[x][y] = new JButton();
				buttons[x][y].setFocusable(false);
				bombGrid[x][y] = true;
				bombsAdded++;
			}
		}

		for (int i = 0; i < buttons.length; i++){
			for (int j = 0; j < buttons[i].length; j++){
				if (buttons[i][j] == null) {
					buttons[i][j] = new JButton();
				}
			}       
		}


		for (int i = 0; i < buttons.length; i++){
			for (int j = 0; j < buttons[i].length; j++){
				frame.add(buttons[i][j]);
				//	buttons[i][j].addActionListener(this);


			}       
		}       
	}
	private void createCountGrid() {
		/*nested for loops is used to iterate through each cell adjacent to the current cell in the bombGrid.
		 *  This includes the 8 cells surrounding 
		 * the current cell: the one directly above, the one directly below, 
		 * the one directly to the left, the one directly to the right, and the 4 diagonals.
		 * 
		 * 
		 * 
		 */
		for (int i = 0; i < countGrid.length; i++) {
			for (int j = 0; j < countGrid[i].length; j++) {
				int count = 0;

				for (int k = i - 1; k <= i + 1; k++) { // checks below and above the row
					for (int m = j - 1; m <= j + 1; m++) { // Checks to the left and right of the column
						if (k >= 0 && k < bombGrid.length && m >= 0 && m < bombGrid[0].length && bombGrid[k][m] == true) {
							count++;
						}
					}
				}
				countGrid[i][j] = count;
				buttons[i][j].addActionListener(this);
			}
		}
	}


	public void showAllBombs() {

		for (int i = 0; i < bombGrid.length; i++){
			for (int j = 0; j <bombGrid[i].length; j++){

				if (bombGrid[i][j] == false ) {

					System.out.printf("%-10s","Row: "+(i+1));
					System.out.printf("Column: "+(j+1)+" ");
					System.out.printf("%-10s", bombGrid[i][j]);

					if (j < bombGrid[i].length - 1) {    
						System.out.print(" ");
					}
				}

				if (bombGrid[i][j] == true ) {
					System.out.printf("%-10s","Row: "+(i+1));
					System.out.printf("Column: "+(j+1)+" ");
					System.out.printf("%-10s",bombGrid[i][j]+ " x");

					if (j < bombGrid[i].length - 1) {    
						System.out.print(" ");
					}
				}
				if ((j + 1) % 10 == 0) {  //if j is divisible by 10 and we have no remainder, for every 10th column we print the next one at the next line 
					System.out.println(); 

				} 
			}       
		}      

	}
	public void showGridCount() {

		int count = 0;
		for (int i = 0; i < countGrid.length; i++){ // the length of the 2 dimensional array is given by the number of rows * number of columns
			for (int j = 0; j < countGrid[i].length; j++){


				System.out.printf("%-10s", countGrid[i][j]);

				if (j < countGrid[i].length - 1) {    
					System.out.print(" ");
				}
				if ((j + 1) % 10 == 0) {  //if j is divisible by 10 and we have no remainder, for every 10th column we print the next one at the next line 
					System.out.println(); 

				} 
			}


		}

	}   
	@Override
	public void actionPerformed(ActionEvent e) {

		ImageIcon icon = new ImageIcon("bomb.png");
		for (int i = 0; i < buttons.length; i++){
			for (int j = 0; j < buttons[i].length; j++){
				if (e.getSource()== buttons[i][j]) { //what we want to do when we click on the button
					if (bombGrid[i][j] == false ) {
						buttons[i][j].setText(String.valueOf(countGrid[i][j]));		
						buttons[i][j].setFocusable(false);
						 adjacentCells();
						
						if (isFull() == true) {
							GameWon();
						}
						
						}
					else {
						for (int row = 0; row < bombGrid.length; row++) {
							for (int col = 0; col < bombGrid[row].length; col++) {
								if (bombGrid[row][col] == true) {
									
									buttons[row][col].setIcon(icon);

								}
							if ((bombGrid[row][col] == false)) {	//only displays the value of non bombs when the game is lost	
								buttons[row][col].setText(String.valueOf(countGrid[row][col]));	
								
							}
							}
						}


						GameOver();
					}
					
				}
			}

			
		}
	}
	public void GameOver() {

		ImageIcon icon = new ImageIcon("mad.png");	
		int answer = JOptionPane.showOptionDialog(null,"Game Over!\nWanna play again?", "MineSweeper",JOptionPane.YES_NO_OPTION,0,icon,null, null);

		if (answer == JOptionPane.YES_NO_OPTION) {
			frame.dispose();
			newGame();

		}
		else {
			System.exit(frame.EXIT_ON_CLOSE);
		}
	}


	public Grid2 newGame() {

		Grid2 grid = new Grid2(numRows,numColumns,numBombs);


		return grid;
	}
	public void GameWon() {

		ImageIcon icon = new ImageIcon("joy.png");	
		int answer = JOptionPane.showOptionDialog(null,"You Won, Great Job!\nWanna play again?", "MineSweeper",JOptionPane.YES_NO_OPTION,0,icon,null, null);

		if (answer == JOptionPane.YES_NO_OPTION) {
			frame.dispose(); //closes previous game so we dont have multiple games open
			newGame();

		}
		else {
			System.exit(frame.EXIT_ON_CLOSE);
		}

				
		}
	
	public boolean isFull() {
		for(int i = 0; i < buttons.length; i++){
			for(int  j = 0; j < buttons[i].length; j++){

				if (bombGrid[i][j] == false) {
					if(buttons[i][j].getText().trim().isEmpty()){
						return false; // grid is not full
					}
				}
			}
		}
		return true;//  theres no empty cell/ grid is full
	}		

	public void adjacentCells() {
	    for (int i = 0; i < buttons.length; i++) {
	        for (int j = 0; j < buttons[i].length; j++) {
	            if (buttons[i][j].getText().equalsIgnoreCase("0")) { // only check for adjacent cells for cells containing "0"
	                displayAdjacentCells(i, j);
	            }
	        }
	    }
	}

	private void displayAdjacentCells(int row, int col) {
	    for (int k = row - 1; k <= row + 1; k++) { // checks above and below the row
	        for (int m = col - 1; m <= col + 1; m++) { // Checks to the left and right of the column
	            if (k >= 0 && k < buttons.length && m >= 0 && m < buttons[row].length && bombGrid[k][m] == false && buttons[k][m].getText().isEmpty()) {
	                buttons[k][m].setText(String.valueOf(countGrid[k][m]));
	                if (countGrid[k][m] == 0) {
	                    displayAdjacentCells(k, m); // display adjacent cells if the adjacent cell has a value of 0 and calls the method again 
	                }
	            }
	        }
	    }
	}


	public static void main(String[] args) {

		Grid2 grid = new Grid2();

	}

}