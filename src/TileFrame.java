import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * TileFrame is a class that extends JFrame
 * 
 * <p>This class creates the JFrame, JPanels and JButtons for the Mosaic program. The default constructor
 * creates a 12 x 12 grid that is randomly populated by Tile instances. The buttons that are added allow
 * the user to interact with the program which can Randomize tiles, randomize single tiles and change the layout.
 * 
 * @author Pap
 * @version 1.0
 *
 */
public class TileFrame extends JFrame {
	private ArrayList<Tile> tileList;						// Set it as private (mistake I made in FaceDraW)
	private int numRows, numCols;							// Sets the layout of the grid
	private Container contentPane = getContentPane();		// Container needs to be accessed in multiple methods
	private JPanel tileGrid = new JPanel();					// Panel needs to be accessed in multiple methods
	
	// Default Constructor
	public TileFrame() {
		// Set the default settings of the Frame
		setDefaultLookAndFeelDecorated(true);				// Fancier window
		setBounds(0, 0, 1200, 800);							// Top Left Corner 1200w x 800h
		setDefaultCloseOperation(TileFrame.EXIT_ON_CLOSE);	// Close the program when clicking Red X		
		setTitle("Mosaic");									// Title of the window
		setRows(12);										// Default 12 Rows
		setCols(12);										// Default 12 Columns
		
		System.out.println("Start paint...");				// First console message
		setWindowContent();									// Creates the layout of the window
		setWindowTiles();									// Adds the Tiles to the window
		
		// Print the tile information to console
		for (Tile t: tileList) {
			System.out.println(t.toString());
		}
		setVisible(true);									// Now you can see the Frame
	
	}
	
	/**
	 * Creates the layout and design for the window.
	 */
	public void setWindowContent() {
		// Add the main content pane for the whole window
		contentPane.setLayout(new BorderLayout());
		
		// Create a new JPanel for the Button and put in pane 
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);	// SOUTH sets it to bottom of Container
		
		// Create a new JButton to Randomize all tiles and put it in the Panel
		JButton randomizeButton = new JButton("Randomize All");
		buttonPanel.add(randomizeButton);
		randomizeButton.setToolTipText("Click this to randomize all tiles");	// Adds tooltip to help user

		// ActionListener looks for the user to press the button and then executes the method
		randomizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				randomizeButtonPressed();
			}
		} );
		
		// Create a new JButton to Randomize ONE tile and put it in the Panel
		JButton randomizeOneButton = new JButton("Randomize One Tile");
		buttonPanel.add(randomizeOneButton);
		randomizeOneButton.setToolTipText("Click this to randomly change one tile"); 	// Adds tooltip to help user

		// ActionListener looks for the user to press the button and then executes the method
		randomizeOneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				randomizeOneButtonPressed();
			}
		} );
		
		
		// Allow the user to input the number of Rows and Columns
		JTextField rowsTextField = new JTextField(Integer.toString(getRows()),3);	// Creates the text box for Rows
		JLabel rowsLabel = new JLabel("Rows");										// Creates the label for Rows
		rowsTextField.setHorizontalAlignment(JTextField.CENTER);					// Aligns the text in the center
		rowsTextField.setToolTipText("Change this to set the number of rows");		// Tooltip to help user

		JTextField colsTextField = new JTextField(Integer.toString(getCols()),3);	// Creates the text box for Columns
		JLabel colsLabel = new JLabel("Columns");									// Creates the label for Columns
		colsTextField.setHorizontalAlignment(JTextField.CENTER);					// Aligns the text in the center
		colsTextField.setToolTipText("Change this to set the number of columns");	// Tooltip to help user

		// Adds the buttons to the Panel
		buttonPanel.add(rowsTextField);
		buttonPanel.add(rowsLabel);
		buttonPanel.add(colsTextField);
		buttonPanel.add(colsLabel);
		
		// Create a new JButton to set the number of rows and columns and put it in the Panel
		JButton setRowColsButton = new JButton("Save Values");
		buttonPanel.add(setRowColsButton);
		setRowColsButton.setToolTipText("Click this to save new rows and columns and automatically regenerate the grid");
		
		// ActionListener looks for the user to press the button and then executes the method
		setRowColsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rowsText = rowsTextField.getText();							// Gets the user input as a String
				String colsText = colsTextField.getText();							// Gets the user input as a String
				int rowsOut = Integer.parseInt(rowsText);							// Converts string to int
				int colsOut = Integer.parseInt(colsText);							// Converts string to int
				setRowsAndCols(rowsOut, colsOut);
			}
		} );
		
	}
	
	/**
	 * Creates the tiles to be displayed in the window. The number of tiles generated will change based
	 * on the user inputted number of rows and columns.
	 */
	public void setWindowTiles() {
		// Add the Grid for the Main Tiles and put it in pane
		contentPane.add(tileGrid, BorderLayout.CENTER);					// Align in center
		tileGrid.setLayout(new GridLayout(getRows(), getCols()));		// Create grid based on user input of size
		
		// Add the tiles to the ArrayList and only add the amount needed
		tileList = new ArrayList<Tile>();
		for (int i = 0; i < (getRows() * getCols()); i++) {
			Tile tile = new Tile();
			tileList.add(tile);											// Adds the tile to the ArrayList
			tileGrid.add(tile);											// Adds the tile to the Grid to be displayed
		}
	}
	
	/**
	 * Randomizes all of the tiles in the grid.
	 */
	public void randomizeButtonPressed() {
		System.out.println("\n\n\n\n\nStart paint...");					// Adds 5 blank lines to format console
		
		// For all of the tiles in the list, create new random values
		for (Tile t: tileList) {
			t.SetRandomValues();
			System.out.println(t.toString());							// Print tile information to console
		}
		repaint();														// Repaints the JPanel to update the information
	}
	
	/**
	 * Randomize the tile for one tile. The tile that is changed is randomly selected from the existing ArrayList
	 */
	public void randomizeOneButtonPressed() {
		Random rand = new Random();
		int randomTile = rand.nextInt(tileList.size());					// Choose a random tile from the ArrayList
		Tile t = tileList.get(randomTile);								// Select the tile
		t.SetRandomValues();											// Randomize the values
		System.out.println("\n\nStart paint...");						// Add 2 blank lines to format console
		System.out.println(t.toString());								// Print tile information to console
		repaint();														// Repaints the JPanel to update the information
	}
	
	/**
	 * Sets the number of rows and columns of the grid and then regenerates the ArrayList and tileGrid
	 * @param rows User input number of rows in the grid
	 * @param cols User input number of columns in the grid
	 */
	public void setRowsAndCols(int rows, int cols) {
		setRows(rows);										// Set Rows
		setCols(cols);										// Set Columns
		tileList.clear();									// Remove all Tiles from ArrayList
		tileGrid.removeAll();								// Remove all Tiles from Grid	
		setWindowTiles();									// Generate new tiles
		System.out.println("\n\n\n\n\nStart paint...");		// Add 5 blank lines to format console
		
		// For each tile in the list, print the information to the console
		for (Tile t: tileList) {
			System.out.println(t.toString());
		}
		
		tileGrid.revalidate();								// Resets the grid to have the proper format
		repaint();											// Repaint the JPanel to update the informtion
	}
	
	/**
	 * Sets the number of rows
	 * @param rowsIn Number of Rows
	 */
	public final void setRows(int rowsIn) { numRows = rowsIn; }
	
	/**
	 * Sets the number of columns
	 * @param rowsIn Number of Columns
	 */
	public final void setCols(int colsIn) { numCols = colsIn; }
	
	/**
	 * @return Returns the number of rows
	 */
	public final int getRows() { return numRows; }
	
	/**
	 * @return Returns the number of columns
	 */
	public final int getCols() { return numCols; }
}
