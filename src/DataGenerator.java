/**
 * @author Ali K. Afridi
 * Last Modified: 11/15/13
 * 
 * This software generates data based on user-preferences
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jxl.Workbook;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DataGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Default Values
		int min_X = 0, max_X = 100, min_Y = 0, max_Y = 100; // location preferences
		boolean periodic = true; //
		boolean default_Settings = true;
		String motionType = "circular";
		int days = 4; // days of data that will be recorded
		double tolerance = 0; // tolerance (meters)
		int num_Periods = 1; // number of periods in a day
		int sampling = 4; // time difference in between measurement (mins)

		// Ask the user the necessary questions about the data
		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Generator");

		//default_Settings = defaultOrCustom(); XXX - UnComment this in order to give the user control over the values

		if (!default_Settings) {
			periodic = periodicity();
			motionType = typeOfMotion(motionType);
			num_Periods = numberOfPeriods(3);
			tolerance = getNumber("the tolerance", 4);
			do {
				days = daysOfData(4);
			} while (days <= 0);
			do {
				sampling = getNumber("the sampling rate", 4);
			} while (sampling <= 0);
			min_X = getNumber("minimum x", min_X);
			do {
				max_X = getNumber("maximum x", max_X);
			} while (max_X <= min_X);
			min_Y = getNumber("minimum y", min_Y);
			do {
				max_Y = getNumber("maximum y", max_Y);
			} while (max_Y <= min_Y);
		}
		
		/*JFrame f = new JFrame("Plotting Points");  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        PointGrapher x = new PointGrapher();
        f.getContentPane().add(x);  
        f.setSize(400,400);  
        f.setLocation(200,200);  
        f.setVisible(true);
*/
		//TODO - DATA Checking for user FUCK-UP
		//TODO - Add a form to make data entry easier

		// Create a file
		
		int rows = 4* 24 * 15 ; //number of rows in the excel file - 4 days * 24 hours per day * 15 data points per hour
		
		JFrame frame=new JFrame(); //creates frame
	    JButton[][] grid; //names the grid of buttons


		try {
			File exlFile = new File("movement_data.xls");
			WritableWorkbook writableWorkbook = Workbook
					.createWorkbook(exlFile);

			WritableSheet writableSheet = writableWorkbook.createSheet(
					"Sheet1", 0);

			for (int i = 0; i < rows; i++) {
				Number x_value = new Number (0, i, 0);
				Number y_value = new Number (1, i, 0);
				
				writableSheet.addCell(x_value);
				writableSheet.addCell(y_value);
			}

			//Write and close the workbook
			writableWorkbook.write();
			writableWorkbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		// TODO - Ask the user the file they would like to save and its location 
		JOptionPane.showMessageDialog(null, "Data Generation Complete");
	}


	private static String typeOfMotion(String default_value) {
		Object[] options = {"circular", "square", "rectangular", "lemniscatic(8)", "random"};
		String inputString = (String)JOptionPane.showInputDialog(
				null,
				"Choose type of motion:\n",
				"Motion Type",
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				"circular");

		if (inputString == null || inputString.length() <= 0)
			inputString = default_value;

		return inputString;
	}

	private static boolean periodicity() {
		Object[] periods = {"periodic", "non-periodic"};
		String periodicity = (String)JOptionPane.showInputDialog(
				null,
				"Choose type of motion:\n",
				"Motion Type",
				JOptionPane.PLAIN_MESSAGE,
				null,
				periods,
				"periodic");

		if (periodicity.equals("non-periodic"))
			return false;

		return true;
	}

	private static boolean defaultOrCustom() {
		Object[] options = {"default", "custom"};
		String input = (String)JOptionPane.showInputDialog(
				null,
				"Choose type of motion:\n",
				"Motion Type",
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				"default");

		if (input.equals("custom"))
			return false;

		return true;
	}

	private static int numberOfPeriods(int default_value){
		String input = JOptionPane.showInputDialog(null, "How many periods should there be in a day?");
		int output = Integer.parseInt(input);
		if (output > 0)
			return output;
		return default_value;
	}

	private static int daysOfData(int default_value){
		String input = JOptionPane.showInputDialog(null, "How many days of data would you like?");
		int output = Integer.parseInt(input);
		if (output > 0)
			return output;
		return default_value;
	}

	private static int getNumber(String value, int default_value){
		String input = JOptionPane.showInputDialog(null, "What would you like for the value of " + value);
		int output = Integer.parseInt(input);
		if (output > 0)
			return output;
		return default_value;
	}
	
	private static int xValueGenerator(int time, int frequency, int min, int max) {
		return time;
	}
	
	private static int yValueGenerator(int time, int frequency, int min, int max) {
		return time;
	}
	/*
    public static void ButtonGrid(int width, int length){ //constructor
            frame.setLayout(new GridLayout(width,length)); //set layout
            grid=new JButton[width][length]; //allocate the size of grid
            for(int y=0; y<length; y++){
                    for(int x=0; x<width; x++){
                            grid[x][y]=new JButton("("+x+","+y+")"); //creates new button    
                            frame.add(grid[x][y]); //adds button to grid
                    }
            }
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); //sets appropriate size for frame
            frame.setVisible(true); //makes frame visible
    }
    
    public static void main(String[] args) {
            new ButtonGrid(3,3);//makes new ButtonGrid with 2 parameters
    }
*/
    
	
}
