import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class AnalyzerSimplified {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String input_file_name = "xls/M0126.xls";
		String output_file_name = "movement_data_analysis.xls";

		int days = 4; //days in the data
		int frame_width = 800;
		int frame_height = 480;

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Analyzer");

		int rows;

		try {

			Workbook wrk1 =  Workbook.getWorkbook(new File(input_file_name));

			Sheet sheet1 = wrk1.getSheet(0);
			rows=sheet1.getRows();

			double [][] data = new double [rows][5]; //Timestamp, X Value, Y Value, Speed, Box #
			String [] directions = new String [rows];
			String directionList = "";
			String x_value_list = "";

			for (int i = 1; i < rows; i++) {
				Cell time_cell = sheet1.getCell(0, i);
				Cell x_cell = sheet1.getCell(1, i);
				Cell y_cell = sheet1.getCell(2, i);

				String time_cell_string = time_cell.getContents();
				String x_cell_string = x_cell.getContents();
				String y_cell_string = y_cell.getContents();

				Double time_double = Double.parseDouble(time_cell_string);
				Double x_double = Double.parseDouble(x_cell_string);
				Double y_double = Double.parseDouble(y_cell_string);
				
				x_value_list += x_cell_string + ",";

				data[i][0] = time_double.intValue();
				data[i][1] = x_double.intValue();
				data[i][2] = y_double.intValue();
				data[i][4] = (y_double.intValue() - 4367535 ) / 35; 
				//System.out.println(data[i][4]);
				if (i == 1) {
					data[i][3] = 0;
					directions[i] = "";
				}
				else {
					double delta_x = (data[i][1]-data[i-1][1]);
					double delta_y = (data[i][2]-data[i-1][2]);

					if (delta_x == 0) {
						if (delta_y > 0) {
							directions[i] = "N";
							directionList += "N,";
						}
						else if (delta_y < 0) {
							directions[i] = "S";
							directionList += "S,";
						}
						else {
							directions[i] = "";
							directionList += " ,";
						}
					}
					else {
						double slope = delta_y/delta_x;
						if (slope > .414214 && slope < 2.41421) {
							//Direction = NE or SW
							if (delta_y > 0) {
								directions[i] = "NE";
								directionList += "NE,";
							}
							else {
								directions[i] = "SW";
								directionList += "SW,";
							}
						}
						else if (slope < -.414214 && slope > -2.41421) {
							//Direction = NW or SE
							if (delta_y > 0) {
								directions[i] = "NW";
								directionList += "NW,";
							}
							else {
								directions[i] = "SE";
								directionList += "SE,";
							}
						}
						else if (slope > 2.41421 || slope < -2.41421) {
							//Direction = N or S
							if (delta_y > 0) {
								directions[i] = "N";
								directionList += "N,";
							}
							else {
								directions[i] = "S";
								directionList += "S,";
							}
						}
						else if (slope > -.414214 && slope < .414214) {
							//Direction = E or W
							if (delta_x > 0) {
								directions[i] = "E";
								directionList += "E,";
							}
							else {
								directions[i] = "W";
								directionList += "W,";
							}
						}
					}

					data[i][3] = (int) Math.sqrt(Math.pow(delta_x,2) + Math.pow(delta_y,2));
				}
			}
			
			File exlFile = new File(output_file_name);
			WritableWorkbook writableWorkbook = Workbook
					.createWorkbook(exlFile);

			WritableSheet writableSheet = writableWorkbook.createSheet("Regions", 0);
			Label time = new Label (0, 0, "Time");
			Label x = new Label (1, 0, "Day 1");
			Label y = new Label (2, 0, "Day 2");
			Label speed = new Label (3, 0, "Day 3");
			Label direction = new Label (4, 0, "Day 4");
			int rows_in_days = 15*24;

			writableSheet.addCell(time);
			writableSheet.addCell(x);
			writableSheet.addCell(y);
			writableSheet.addCell(speed);
			writableSheet.addCell(direction);

			int a=1;
			for (int i = 1; i < rows/4; i++){
				Number time_stamp = new Number (0, a, data[i][0]);
				Number x_value = new Number (1, a, data[i][4]);
				Number y_value = new Number (2, a, data[i+(rows_in_days)][4]);
				Number speed_value = new Number (3, a, data[i+(rows_in_days)*2][4]);
				Number direction_value = new Number (4, a, data[i+(rows_in_days)*3][4]);

				writableSheet.addCell(time_stamp);
				writableSheet.addCell(x_value);
				writableSheet.addCell(y_value);
				writableSheet.addCell(speed_value);
				writableSheet.addCell(direction_value);
				a++;
			}
			
			WritableSheet writableSheet2 = writableWorkbook.createSheet("Directions", 1);
			Label time2 = new Label (0, 0, "Time");
			Label x2 = new Label (1, 0, "Day 1");
			Label y2 = new Label (2, 0, "Day 2");
			Label speed2 = new Label (3, 0, "Day 3");
			Label direction2 = new Label (4, 0, "Day 4");
			writableSheet2.addCell(time2);
			writableSheet2.addCell(x2);
			writableSheet2.addCell(y2);
			writableSheet2.addCell(speed2);
			writableSheet2.addCell(direction2);

			a=1;
			for (int i = 1; i < rows/4; i++){
				Number time_stamp = new Number (0, a, data[i][0]);
				Label x_value = new Label (1, a, directions[i]);
				Label y_value = new Label (2, a, directions[i+(rows_in_days)]);
				Label speed_value = new Label (3, a, directions[i+(rows_in_days)*2]);
				Label direction_value = new Label (4, a, directions[i+(rows_in_days)*3]);

				writableSheet2.addCell(time_stamp);
				writableSheet2.addCell(x_value);
				writableSheet2.addCell(y_value);
				writableSheet2.addCell(speed_value);
				writableSheet2.addCell(direction_value);
				a++;
			}
			
			
			WritableSheet writableSheet3 = writableWorkbook.createSheet("Speed", 2);
			Label time3 = new Label (0, 0, "Time");
			Label x3 = new Label (1, 0, "Day 1");
			Label y3 = new Label (2, 0, "Day 2");
			Label speed3 = new Label (3, 0, "Day 3");
			Label direction3 = new Label (4, 0, "Day 4");

			writableSheet3.addCell(time3);
			writableSheet3.addCell(x3);
			writableSheet3.addCell(y3);
			writableSheet3.addCell(speed3);
			writableSheet3.addCell(direction3);

			a=1;
			for (int i = 1; i < rows/4; i++){
				Number time_stamp = new Number (0, a, data[i][0]);
				Number x_value = new Number (1, a, data[i][3]);
				Number y_value = new Number (2, a, data[i+(rows_in_days)][3]);
				Number speed_value = new Number (3, a, data[i+(rows_in_days)*2][3]);
				Number direction_value = new Number (4, a, data[i+(rows_in_days)*3][3]);

				writableSheet3.addCell(time_stamp);
				writableSheet3.addCell(x_value);
				writableSheet3.addCell(y_value);
				writableSheet3.addCell(speed_value);
				writableSheet3.addCell(direction_value);
				a++;
			}
			

			//Write and close the workbook
			writableWorkbook.write();
			writableWorkbook.close();

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}


		JOptionPane.showMessageDialog(null, "Data Analysis Complete");
	}
}