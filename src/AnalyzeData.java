/**
 * @author Ali K. Afridi
 * 
 * Last Modified: 12/17/13
 * 
 * This software analyzes the livestock movement data
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class AnalyzeData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Analyzer");

		int rows;

		try {

			Workbook wrk1 =  Workbook.getWorkbook(new File("xls/M0126.xls"));

			Sheet sheet1 = wrk1.getSheet(0);
			rows=sheet1.getRows();

			double [][] data = new double [rows][5];
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
				System.out.println(data[i][4]);
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
			}// end of for loop

			System.out.println(lrs(directionList));
			
			
			/* Compass Analysis
			System.out.println(directionList);
			boolean find_subsequence = true;
			int length = 3;
			String search_string;
			int repetitions = 0;
			int max_repetitions = 0;
			String max_repetition_sequence = "";
			String remainderList = directionList;
			
			while (find_subsequence) {
				for (int a = 0; a < directionList.length(); a++) {
					search_string = directionList.substring(a, a + 3);
					remainderList = directionList.substring(0, a) + directionList.substring(a+3);
					while (remainderList.indexOf(search_string)!=-1) {
						repetitions++;
					}
					if (repetitions > max_repetitions) {
						max_repetitions = repetitions;
						max_repetition_sequence = search_string;
					}
				}
			}*/

			/* Output File 
			File exlFile = new File("movement_data_editable.xls");
			WritableWorkbook writableWorkbook = Workbook
					.createWorkbook(exlFile);

			WritableSheet writableSheet = writableWorkbook.createSheet("Sheet1", 0);
			Label time = new Label (0, 0, "Time");
			Label x = new Label (1, 0, "X");
			Label y = new Label (2, 0, "Y");
			Label speed = new Label (3, 0, "Y");
			Label direction = new Label (4, 0, "Y");

			writableSheet.addCell(time);
			writableSheet.addCell(x);
			writableSheet.addCell(y);
			writableSheet.addCell(speed);
			writableSheet.addCell(direction);

			for (int i = 0; i < rows; i++){
				Number time_stamp = new Number (0, i, data[i][0]);
				Number x_value = new Number (1, i, data[i][1]);
				Number y_value = new Number (2, i, data[i][2]);
				Number speed_value = new Number (3, i, data[i][3]);
				Label direction_value = new Label (4, i, directions[i]);

				writableSheet.addCell(time_stamp);
				writableSheet.addCell(x_value);
				writableSheet.addCell(y_value);
				writableSheet.addCell(speed_value);
				writableSheet.addCell(direction_value);

			}

			//Write and close the workbook
			writableWorkbook.write();
			writableWorkbook.close();*/

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}


		JOptionPane.showMessageDialog(null, "Data Analysis Complete");
	}
	
	// The code below is a modification of the lrs and lcp code found at http://introcs.cs.princeton.edu/java/42sort/LRS.java.html
	// The code has been modified to work better for the problem at hand
	public static String lrs(String s) {

        // form the N suffixes
        int N  = s.length();
        String[] suffixes = new String[N];
        for (int i = 0; i < N; i++) {
            suffixes[i] = s.substring(i, N);
        }

        // sort them
        Arrays.sort(suffixes);

        // find longest repeated substring by comparing adjacent sorted suffixes
        String lrs = "";
        for (int i = 0; i < N - 1; i++) {
            String x = lcp(suffixes[i], suffixes[i+1]);
            if (x.length() > lrs.length())
                lrs = x;
        }
        return lrs;
    }
	
	// return the longest common prefix of s and t
    public static String lcp(String s, String t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i))
                return s.substring(0, i);
        }
        return s.substring(0, n);
    }
}
