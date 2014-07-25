import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class AutoPeriodicity {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Analyzer");

		String[] input = {"M0110", "M0126", "M0138", "M0153", "M0154", "M0161", "M0162", "M0163"};
		for (int i = 0; i < input.length; i++) {
			analyzeDirections("xls/" + input[i] + ".xls", "results/output_" + input[i] + "_directions.txt");
			//analyzeSpeeds("xls/" + input[i] + ".xls", "results/output_" + input[i] + "_directions.txt");
		}

		JOptionPane.showMessageDialog(null, "Data Analysis Complete");

		return ;
	}

	public static void analyzeSpeeds(String input_file_name, String output_file_name) {

		AutoPeriodicityHelpers helper = new AutoPeriodicityHelpers();

		try {
			Workbook wrk1 =  Workbook.getWorkbook(new File(input_file_name));

			Sheet sheet1 = wrk1.getSheet(0);
			int rows=sheet1.getRows();

			double [][] data = new double [rows][5];
			double [] speeds = new double [rows];

			for (int i = 2; i < rows; i++) {
				Cell time_cell = sheet1.getCell(0, i);
				Cell x_cell = sheet1.getCell(1, i);
				Cell y_cell = sheet1.getCell(2, i);
				
				Cell old_time_cell = sheet1.getCell(0, i-1);
				Cell old_x_cell = sheet1.getCell(1,i-1);
				Cell old_y_cell = sheet1.getCell(2,i-1);

				
				String time_cell_string = time_cell.getContents();
				String x_cell_string = x_cell.getContents();
				String y_cell_string = y_cell.getContents();
				
				String old_time_cell_string = old_time_cell.getContents();
				String old_x_cell_string = old_x_cell.getContents();
				String old_y_cell_string = old_y_cell.getContents();

			
				Double time = Double.parseDouble(time_cell_string) - Double.parseDouble(old_time_cell_string);
				Double x_double = Double.parseDouble(x_cell_string);
				Double y_double = Double.parseDouble(y_cell_string);
				Double old_x_double = Double.parseDouble(old_x_cell_string);
				Double old_y_double = Double.parseDouble(old_y_cell_string);
				
				speeds[i-2] = AutoPeriodicityHelpers.calculateSpeed(x_double, y_double, old_x_double, old_y_double, time);
			}

			PrintWriter writer = new PrintWriter(output_file_name, "UTF-8");

			//boolean repetitions = true;
			int start_index;
			int end_index;

			boolean first = true;
			start_index = 0;
			end_index = 0;
			int counter = 0;
			String sub;
			int sub_length = 3;
			boolean repetitions = true;

			while (repetitions) {
				writer.println("\t\t\tRepetitions of length: " + sub_length);
				repetitions = false;
				/*while (end_index >= 0 && end_index < speeds.length-2) {
					if (first) {
						first = false;
						for (int i = 0; i < sub_length; i++)
							end_index = directionList.indexOf(",", end_index+1); // end_index is now the index of the third comma
						sub = directionList.substring(start_index,end_index+1);
					}
					else {
						start_index = directionList.indexOf(",", start_index+1);
						end_index = directionList.indexOf(",", end_index+1);
						sub = directionList.substring(start_index+1,end_index+1);
					}

					if (counter < 3) {
						counter++;
						System.out.println("Substring: " + sub);
					}

					int count = 0;
					int last_index = 0;
					// Calculate the number of repetitions
					while (last_index != -1) {
						last_index = directionList.indexOf(sub, last_index);
						if (last_index != -1) {
							count++;
							last_index += sub.length();
						}
					}
					if (count >= 2) {
						writer.println("Substring: \"" + sub + "\"\tCount: " + count);
						repetitions = true;
					}
				}*/
				sub_length++;
				start_index = 0;
				end_index = 0;
				counter = 0;
				first = true;
			}

			writer.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void analyzeDirections(String input_file_name, String output_file_name) {

		AutoPeriodicityHelpers helper = new AutoPeriodicityHelpers();

		try {
			Workbook wrk1 =  Workbook.getWorkbook(new File(input_file_name));

			Sheet sheet1 = wrk1.getSheet(0);
			int rows=sheet1.getRows();

			double [][] data = new double [rows][5];
			String [] directions = new String [rows];
			String directionList = "";

			for (int i = 1; i < rows; i++) {
				Cell x_cell = sheet1.getCell(1, i);
				Cell y_cell = sheet1.getCell(2, i);

				String x_cell_string = x_cell.getContents();
				String y_cell_string = y_cell.getContents();

				Double x_double = Double.parseDouble(x_cell_string);
				Double y_double = Double.parseDouble(y_cell_string);

				data[i][1] = x_double.intValue();
				data[i][2] = y_double.intValue();

				if (i == 1) {
					data[i][3] = 0;
					directions[i] = "";
				}
				else {
					double delta_x = (data[i][1]-data[i-1][1]);
					double delta_y = (data[i][2]-data[i-1][2]);

					directionList += AutoPeriodicityHelpers.calculateDirection(delta_x, delta_y);
				}
			}

			PrintWriter writer = new PrintWriter(output_file_name, "UTF-8");

			//boolean repetitions = true;
			int start_index;
			int end_index;

			System.out.println("First 20 chars of directionList: " + directionList.substring(0,directionList.indexOf(",", 100)));

			boolean first = true;
			start_index = 0;
			end_index = 0;
			int counter = 0;
			String sub;
			int sub_length = 3;
			boolean repetitions = true;

			while (repetitions) {
				writer.println("\t\t\tSubstrings of length: " + sub_length);
				repetitions = false;
				while (end_index >= 0 && end_index < directionList.length()-2) {
					if (first) {
						first = false;
						for (int i = 0; i < sub_length; i++)
							end_index = directionList.indexOf(",", end_index+1); // end_index is now the index of the third comma
						sub = directionList.substring(start_index,end_index+1);
					}
					else {
						start_index = directionList.indexOf(",", start_index+1);
						end_index = directionList.indexOf(",", end_index+1);
						sub = directionList.substring(start_index+1,end_index+1);
					}

					if (counter < 3) {
						counter++;
						System.out.println("Substring: " + sub);
					}

					int count = 0;
					int last_index = 0;
					// Calculate the number of repetitions
					while (last_index != -1) {
						last_index = directionList.indexOf(sub, last_index);
						if (last_index != -1) {
							count++;
							last_index += sub.length();
						}
					}
					if (count >= 2) {
						writer.println("Substring: \"" + sub + "\"\tCount: " + count);
						repetitions = true;
					}
				}
				sub_length++;
				start_index = 0;
				end_index = 0;
				counter = 0;
				first = true;
			}

			writer.close();


		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}