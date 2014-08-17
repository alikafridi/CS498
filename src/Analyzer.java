import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class Analyzer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Analyzer");
		
		String[] input = {"M0110", "M0126", "M0138", "M0153", "M0154", "M0161", "M0162", "M0163"};
		String[] output = {"M0110", "M0126", "M0138", "M0153", "M0154", "M0161", "M0162", "M0163"};
		for (int i = 0; i < input.length; i++) {
			analyze("xls/" + input[i] + ".xls", "output_" + output[i] + ".xls");
		}

		JOptionPane.showMessageDialog(null, "Data Analysis Complete");
	}

	public static void analyze(String input_file_name, String output_file_name) {

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
							//directions[i] = "N";
							directions[i] = "1";
							directionList += "N,";
						}
						else if (delta_y < 0) {
							//directions[i] = "S";
							directions[i] = "5";
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
								//directions[i] = "NE";
								directions[i] = "2";
								directionList += "NE,";
							}
							else {
								//directions[i] = "SW";
								directions[i] = "6";
								directionList += "SW,";
							}
						}
						else if (slope < -.414214 && slope > -2.41421) {
							//Direction = NW or SE
							if (delta_y > 0) {
								//directions[i] = "NW";
								directions[i] = "8";
								directionList += "NW,";
							}
							else {
								//directions[i] = "SE";
								directions[i] = "4";
								directionList += "SE,";
							}
						}
						else if (slope > 2.41421 || slope < -2.41421) {
							//Direction = N or S
							if (delta_y > 0) {
								//directions[i] = "N";
								directions[i] = "1";
								directionList += "N,";
							}
							else {
								//directions[i] = "S";
								directions[i] = "5";
								directionList += "S,";
							}
						}
						else if (slope > -.414214 && slope < .414214) {
							//Direction = E or W
							if (delta_x > 0) {
								//directions[i] = "E";
								directions[i] = "3";
								directionList += "E,";
							}
							else {
								//directions[i] = "W";
								directions[i] = "7";
								directionList += "W,";
							}
						}
					}

					data[i][3] = (int) Math.sqrt(Math.pow(delta_x,2) + Math.pow(delta_y,2));
				}
			}

			File exlFile = new File(output_file_name);
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);

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
			// Create cell font and format
			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			cellFont.setBoldStyle(WritableFont.BOLD);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(Colour.LIGHT_GREEN);

			WritableFont cellFont2 = new WritableFont(WritableFont.ARIAL, 10);

			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont2);

			int a = 1;
			for (int i = 1; i < rows/4; i++){
				double data1 = data[i][4];
				double data2 = data[i+(rows_in_days)][4];
				double data3 = data[i+(rows_in_days)*2][4];
				double data4 = data[i+(rows_in_days)*3][4]; 

				boolean color1 = false;
				boolean color2 = false;
				boolean color3 = false;
				boolean color4 = false;

				if (data1 == data2 && data1 == data3) {
					color1 = true; 
					color2 = true;
					color3 = true;
					color4 = false;
				} else if (data1 == data3 && data1 == data4) {
					color1 = true;
					color2 = false; 
					color3 = true;
					color4 = true;
				} else if (data1 == data2 && data1 == data4) {
					color1 = true;
					color2 = true; 
					color3 = false;
					color4 = true;
				} else if (data2 == data3 && data2 == data4) {
					color1 = false;
					color2 = true; 
					color3 = true;
					color4 = true;
				} else {
					color1 = false;
					color2 = false; 
					color3 = false;
					color4 = false;
				}

				Number time_stamp = new Number (0, a, data[i][0]);
				Number day1 = new Number (1, a, data1, (color1)?cellFormat:cellFormat2);
				Number day2 = new Number (2, a, data2, (color2)?cellFormat:cellFormat2);
				Number day3 = new Number (3, a, data3, (color3)?cellFormat:cellFormat2);
				Number day4 = new Number (4, a, data4, (color4)?cellFormat:cellFormat2);

				writableSheet.addCell(time_stamp);
				writableSheet.addCell(day1);
				writableSheet.addCell(day2);
				writableSheet.addCell(day3);
				writableSheet.addCell(day4);
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
				String data1 = directions[i];
				String data2 = directions[i+(rows_in_days)];
				String data3 = directions[i+(rows_in_days)*2];
				String data4 = directions[i+(rows_in_days)*3]; 

				boolean color1 = false;
				boolean color2 = false;
				boolean color3 = false;
				boolean color4 = false;

				if (data1.equals(data2) && data1.equals(data3)) {
					color1 = true; 
					color2 = true;
					color3 = true;
					color4 = false;
				} else if (data1.equals(data3) && data1.equals(data4)) {
					color1 = true;
					color2 = false; 
					color3 = true;
					color4 = true;
				} else if (data1.equals(data2) && data1.equals(data4)) {
					color1 = true;
					color2 = true; 
					color3 = false;
					color4 = true;
				} else if (data2.equals(data3) && data2.equals(data4)) {
					color1 = false;
					color2 = true; 
					color3 = true;
					color4 = true;
				} else {
					color1 = false;
					color2 = false; 
					color3 = false;
					color4 = false;
				}

				Number time_stamp = new Number (0, a, data[i][0]);
				Label x_value = new Label (1, a, directions[i],(color1)?cellFormat:cellFormat2);
				Label y_value = new Label (2, a, directions[i+(rows_in_days)],(color2)?cellFormat:cellFormat2);
				Label speed_value = new Label (3, a, directions[i+(rows_in_days)*2],(color3)?cellFormat:cellFormat2);
				Label direction_value = new Label (4, a, directions[i+(rows_in_days)*3],(color4)?cellFormat:cellFormat2);

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
	}
}