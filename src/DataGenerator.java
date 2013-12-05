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
		boolean default_Settings = false; //XXX - Change to true in order to make it easier for the user
		String motionType = "circular";
		int days = 4; // days of data that will be recorded
		double tolerance = 0; // tolerance (meters)
		int num_Periods = 1; // number of periods in a day
		int sampling = 4; // time difference in between measurement (mins)

		// Ask the user the necessary questions about the data
		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Generator");

		//default_Settings = defaultOrCustom(); XXX - UnComment this in order to give the user control over the values

		if (!default_Settings) {
			motionType = typeOfMotion(motionType);
			//num_Periods = numberOfPeriods(3);
			tolerance = getNumber("the tolerance", .05);
		}
		
		// Create a file
		
		int rows = 4 * 24 * 15 ; //number of rows in the excel file - 4 days * 24 hours per day * 15 data points per hour (every 4 mins)
		
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
				// Math.sin(double x); - returns a double 
				
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

	private static double getNumber(String value, double default_value){
		String input = JOptionPane.showInputDialog(null, "What would you like for the value of " + value);
		double output = Double.parseDouble(input);
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
}
