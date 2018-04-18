import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/**
 * Tile Class
 * 
 * <P> Tile extends JPanel and is how the tiles are constructed for the mosaic program.
 * 
 * @author Dan Paplaczyk
 * @version 1.0;
 */

public class Tile extends JPanel {

	private int red, green, blue, shape, shapeWidth, shapeHeight, fontSize;
	private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private char randLetter;
	private String letter, fontColor, typeShape;
	
	/**
	 * Constructor (Default)
	 */
	public Tile() {
		super();
		SetRandomValues();
	}
	
	/**
	 * Sets random values for the different attributes that make up a Tile. 
	 */	
	final public void SetRandomValues() {
		red = GetNumberBetween(0,255);							// Red RGB Value
		green = GetNumberBetween(0,255);						// Green RGB Value
		blue = GetNumberBetween(0,255);							// Blue RGB Value
		shape = GetNumberBetween(0,1);							// 0 is Square, 1 is Circle
		randLetter = alphabet.charAt(GetNumberBetween(0,25));	// Random Letter
		letter = Character.toString(randLetter);				// Convert to string to draw on shape
		typeShape = (shape == 0) ? "Rectangle" : "Oval";		// Produce a String for the shape
	}
	
	/**
	 * Returns a Random number between 2 values. This code was originally
	 * written by Eric Pogue and adapted here because it was very well written and works great.
	 * @param min The smallest value the RNG can be
	 * @param max The largest value the RNG can be
	 * @return A random number between min and max
	 */
	private static int GetNumberBetween(int min, int max) {
		Random rand = new Random();
		return min + rand.nextInt(max - min + 1);
	}
	
	/**
	 * Returns a font color of white or black depending on the overall luminosity of the RNG
	 * provided color. The deisgn of the formula comes from the W3C Recommendation for font
	 * and color readability based on background colors.
	 * @param redIn Value of red in RGB format
	 * @param greenIn Value of green in RGB format
	 * @param blueIn Value of blue in RGB format
	 * @return Value of 0 or 1 to set the font color to black or white
	 */
	private static int SetFontColor(int redIn, int greenIn, int blueIn) {
		double luminosity = 0.0;
		luminosity = (redIn * 0.299) + (greenIn * 0.587) + (blueIn * 0.114);	// W3C Recommendations
		
		int colorFont = (luminosity > 186) ? 0 : 1;
				
		return colorFont;
	}
	
	/**
	 * Draws the tiles in the proper shape with the random colors and random letters.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		shapeWidth = getWidth() - 10;						// Sets shape width to 10 less than full frame size
		shapeHeight = getHeight() - 10;						// Sets shape height to 10 less than full frame size
		
		g.setColor(new Color(red, green, blue));				// Sets color of shape
		
		// If the shape RNG is 0, draw a Rectangle, otherwise draw a circle
		if (shape == 0) {
			g.fillRect(5, 5, shapeWidth, shapeHeight);
		}
		else if (shape == 1) {
			g.fillOval(5, 5, shapeWidth, shapeHeight);
		}
		
		// Set color of the font, if 0 it is black, if 1 it is white
		int colorFont = SetFontColor(red, green, blue);
		if (colorFont == 0) {
			g.setColor(Color.BLACK);
			fontColor = "Black";
		}
		else {
			g.setColor(Color.WHITE);
			fontColor = "White";
		}
		
		// Sets the font size to be half the size of the shape, either height or width
		fontSize = Math.min(shapeHeight / 2, shapeWidth / 2);
		g.setFont(new Font("Courier", Font.BOLD, fontSize));	// fixed width font
		int letterXpos = shapeWidth / 2 - (fontSize / 5);		// Attempt to center
		int letterYpos = shapeHeight / 2 + (fontSize / 2);		// Attempt to center
		g.drawString(letter,  letterXpos,  letterYpos);			// Draw the Letter
		
	}
	
	/**
	 * Creates a string of the information about each tile. It is formatted to work with JSON.
	 */
	public String toString() {
		return String.format("{ \"tile\": { \"shape\": \"%s\", \"shapeRGB\": \"%d, %d, %d\", \"width\": \"%d\", \"height\": \"%d\", \"fontSize\": \"%d\", \"fontColor\": \"%s\", \"letter\": \"%s\", } }", typeShape, red, green, blue, shapeWidth, shapeHeight, fontSize, fontColor, letter);
	}
}
