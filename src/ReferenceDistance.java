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

/**
 * 
 */

/**
 * @author Ali K. Afridi
 *
 */
public class ReferenceDistance {
	
	public static int max_x = -1;
	public static int max_y = -1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Analyzer");
		
		addToSheet("xls/M0110.xls", "output/M0110.xls");
		addToSheet("xls/M0126.xls", "output/M0126.xls");
		addToSheet("xls/M0138.xls", "output/M0138.xls");
		addToSheet("xls/M0153.xls", "output/M0153.xls");
		addToSheet("xls/M0154.xls", "output/M0154.xls");
		addToSheet("xls/M0161.xls", "output/M0161.xls");
		addToSheet("xls/M0162.xls", "output/M0162.xls");
		addToSheet("xls/M0163.xls", "output/M0163.xls");

		JOptionPane.showMessageDialog(null, "Data Analysis Complete");
	}
	
	
	public static void addToSheet (String input_file_name, String output_file_name) {

		// Picks 9 reference points which are along the boundary and the center. (need to know low_x, low_y, max_x, max_y)
		double x_min = 317900;
		double x_max = 317998;
		double y_min = 4367500;
		double y_max = 4367815;
		
		int rows;

		try {

			Workbook wrk1 =  Workbook.getWorkbook(new File(input_file_name));

			Sheet sheet1 = wrk1.getSheet(0);
			rows = sheet1.getRows();

			double [][] data = new double [rows][9]; //distance from reference 1,2,3,...,8,9
			
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
				
				data[i][0] = distance(x_min, y_min, x_double, y_double); // (top left)
				data[i][1] = distance((x_min+x_max)/2, y_min, x_double, y_double); // (top center)
				data[i][2] = distance(x_max, y_min, x_double, y_double); // (top right)
				
				data[i][3] = distance(x_min, (y_min+y_max)/2, x_double, y_double); // (center left)
				data[i][4] = distance((x_min+x_max)/2, (y_min+y_max)/2, x_double, y_double); // (center)
				data[i][5] = distance(x_max, (y_min+y_max)/2, x_double, y_double); // (center right)
				
				data[i][6] = distance(x_min, y_max, x_double, y_double); // (bottom left)
				data[i][7] = distance((x_min+x_max)/2, y_max, x_double, y_double); // (bottom center)
				data[i][8] = distance(x_max, y_max, x_double, y_double); // (bottom right)
				
			}
			
			File exlFile = new File(output_file_name);
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);

			WritableSheet writableSheet = writableWorkbook.createSheet("Day 1", 0);
			
			Label ref1 = new Label (0, 0, "Top Left");
			Label ref2 = new Label (1, 0, "Top Center");
			Label ref3 = new Label (2, 0, "Top Right");
			Label ref4 = new Label (3, 0, "Center Left");
			Label ref5 = new Label (4, 0, "Center");
			Label ref6 = new Label (5, 0, "Center Right");
			Label ref7 = new Label (6, 0, "Bottom Left");
			Label ref8 = new Label (7, 0, "Bottom Center");
			Label ref9 = new Label (8, 0, "Bottom Right");
			

			writableSheet.addCell(ref1);
			writableSheet.addCell(ref2);
			writableSheet.addCell(ref3);
			writableSheet.addCell(ref4);
			writableSheet.addCell(ref5);
			writableSheet.addCell(ref6);
			writableSheet.addCell(ref7);
			writableSheet.addCell(ref8);
			writableSheet.addCell(ref9);
			
			for (int i = 1; i < rows/4; i++){
				Number value1 = new Number (0, i , data[i][0]);
				Number value2 = new Number (1, i , data[i][1]);
				Number value3 = new Number (2, i , data[i][2]);
				Number value4 = new Number (3, i , data[i][3]);
				Number value5 = new Number (4, i , data[i][4]);
				Number value6 = new Number (5, i , data[i][5]);
				Number value7 = new Number (6, i , data[i][6]);
				Number value8 = new Number (7, i , data[i][7]);
				Number value9 = new Number (8, i , data[i][8]);
				
				writableSheet.addCell(value1);
				writableSheet.addCell(value2);
				writableSheet.addCell(value3);
				writableSheet.addCell(value4);
				writableSheet.addCell(value5);
				writableSheet.addCell(value6);
				writableSheet.addCell(value7);
				writableSheet.addCell(value8);
				writableSheet.addCell(value9);
			}	
			
			WritableSheet writableSheet2 = writableWorkbook.createSheet("Day 2", 1);

			Label ref21 = new Label (0, 0, "Top Left");
			Label ref22 = new Label (1, 0, "Top Center");
			Label ref23 = new Label (2, 0, "Top Right");
			Label ref24 = new Label (3, 0, "Center Left");
			Label ref25 = new Label (4, 0, "Center");
			Label ref26 = new Label (5, 0, "Center Right");
			Label ref27 = new Label (6, 0, "Bottom Left");
			Label ref28 = new Label (7, 0, "Bottom Center");
			Label ref29 = new Label (8, 0, "Bottom Right");
			
			writableSheet2.addCell(ref21);
			writableSheet2.addCell(ref22);
			writableSheet2.addCell(ref23);
			writableSheet2.addCell(ref24);
			writableSheet2.addCell(ref25);
			writableSheet2.addCell(ref26);
			writableSheet2.addCell(ref27);
			writableSheet2.addCell(ref28);
			writableSheet2.addCell(ref29);
			
			int a = 1;
			for (int i = rows/4; i < rows/2; i++){
				Number value1 = new Number (0, a , data[i][0]);
				Number value2 = new Number (1, a , data[i][1]);
				Number value3 = new Number (2, a , data[i][2]);
				Number value4 = new Number (3, a , data[i][3]);
				Number value5 = new Number (4, a , data[i][4]);
				Number value6 = new Number (5, a , data[i][5]);
				Number value7 = new Number (6, a , data[i][6]);
				Number value8 = new Number (7, a , data[i][7]);
				Number value9 = new Number (8, a , data[i][8]);
				
				writableSheet2.addCell(value1);
				writableSheet2.addCell(value2);
				writableSheet2.addCell(value3);
				writableSheet2.addCell(value4);
				writableSheet2.addCell(value5);
				writableSheet2.addCell(value6);
				writableSheet2.addCell(value7);
				writableSheet2.addCell(value8);
				writableSheet2.addCell(value9);
				a++;
			}
			
			WritableSheet writableSheet3 = writableWorkbook.createSheet("Day 3", 2);

			Label ref31 = new Label (0, 0, "Top Left");
			Label ref32 = new Label (1, 0, "Top Center");
			Label ref33 = new Label (2, 0, "Top Right");
			Label ref34 = new Label (3, 0, "Center Left");
			Label ref35 = new Label (4, 0, "Center");
			Label ref36 = new Label (5, 0, "Center Right");
			Label ref37 = new Label (6, 0, "Bottom Left");
			Label ref38 = new Label (7, 0, "Bottom Center");
			Label ref39 = new Label (8, 0, "Bottom Right");
			
			writableSheet3.addCell(ref31);
			writableSheet3.addCell(ref32);
			writableSheet3.addCell(ref33);
			writableSheet3.addCell(ref34);
			writableSheet3.addCell(ref35);
			writableSheet3.addCell(ref36);
			writableSheet3.addCell(ref37);
			writableSheet3.addCell(ref38);
			writableSheet3.addCell(ref39);
			
			a = 1;
			for (int i = rows/2; i < 3*rows/4; i++){
				Number value1 = new Number (0, a , data[i][0]);
				Number value2 = new Number (1, a , data[i][1]);
				Number value3 = new Number (2, a , data[i][2]);
				Number value4 = new Number (3, a , data[i][3]);
				Number value5 = new Number (4, a , data[i][4]);
				Number value6 = new Number (5, a , data[i][5]);
				Number value7 = new Number (6, a , data[i][6]);
				Number value8 = new Number (7, a , data[i][7]);
				Number value9 = new Number (8, a , data[i][8]);
				
				writableSheet3.addCell(value1);
				writableSheet3.addCell(value2);
				writableSheet3.addCell(value3);
				writableSheet3.addCell(value4);
				writableSheet3.addCell(value5);
				writableSheet3.addCell(value6);
				writableSheet3.addCell(value7);
				writableSheet3.addCell(value8);
				writableSheet3.addCell(value9);
				a++;
			}
			
			WritableSheet writableSheet4 = writableWorkbook.createSheet("Day 4", 3);

			Label ref41 = new Label (0, 0, "Top Left");
			Label ref42 = new Label (1, 0, "Top Center");
			Label ref43 = new Label (2, 0, "Top Right");
			Label ref44 = new Label (3, 0, "Center Left");
			Label ref45 = new Label (4, 0, "Center");
			Label ref46 = new Label (5, 0, "Center Right");
			Label ref47 = new Label (6, 0, "Bottom Left");
			Label ref48 = new Label (7, 0, "Bottom Center");
			Label ref49 = new Label (8, 0, "Bottom Right");
			
			writableSheet4.addCell(ref41);
			writableSheet4.addCell(ref42);
			writableSheet4.addCell(ref43);
			writableSheet4.addCell(ref44);
			writableSheet4.addCell(ref45);
			writableSheet4.addCell(ref46);
			writableSheet4.addCell(ref47);
			writableSheet4.addCell(ref48);
			writableSheet4.addCell(ref49);
			a=1;
			for (int i = 3*rows/4; i < rows; i++){
				Number value1 = new Number (0, a , data[i][0]);
				Number value2 = new Number (1, a , data[i][1]);
				Number value3 = new Number (2, a , data[i][2]);
				Number value4 = new Number (3, a , data[i][3]);
				Number value5 = new Number (4, a , data[i][4]);
				Number value6 = new Number (5, a , data[i][5]);
				Number value7 = new Number (6, a , data[i][6]);
				Number value8 = new Number (7, a , data[i][7]);
				Number value9 = new Number (8, a , data[i][8]);
				
				writableSheet4.addCell(value1);
				writableSheet4.addCell(value2);
				writableSheet4.addCell(value3);
				writableSheet4.addCell(value4);
				writableSheet4.addCell(value5);
				writableSheet4.addCell(value6);
				writableSheet4.addCell(value7);
				writableSheet4.addCell(value8);
				writableSheet4.addCell(value9);
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
	
	public static double distance(double x_min, double y_min, Double x_double, Double y_double) {
		double result = 0.0;
		double x_distance_squared = Math.pow(x_double-x_min, 2);
		double y_distance_squared = Math.pow(y_double-y_min, 2);
		result = Math.sqrt(x_distance_squared + y_distance_squared);
		return result;
	}
	
	public static void calculateReferencePoint(String input_file_name) {
		try {
			int [][] data = new int [315][98];
			
			for (int h = 0; h < 315; h++) {
				for (int w = 0; w < 98; w++) {
					data[h][w] = 0;
				}
			}
			
			Workbook wrk1 =  Workbook.getWorkbook(new File(input_file_name));
			Sheet sheet1 = wrk1.getSheet(0);
			int rows=sheet1.getRows();
			
			for (int i = 1; i < rows; i++) {

				Cell x_cell = sheet1.getCell(1, i);
				Cell y_cell = sheet1.getCell(2, i);

				String x_cell_string = x_cell.getContents();
				String y_cell_string = y_cell.getContents();

				int x_int = Integer.parseInt(x_cell_string);
				int y_int = Integer.parseInt(y_cell_string);
				
				int x_loc = x_int - 317901;
				int y_loc = y_int - 4367501;
				
				if (x_loc < 99 && y_loc < 316) {
					data[y_loc][y_loc]++;
				}
				else {
					System.out.println("Data is not in valid length. x: " + x_loc + ". y: " + y_loc);
				}
			}
			
			int max_score = 0;
			
			for (int h = 0; h < 315; h++) {
				for (int w = 0; w < 98; w++) {
					if (data[h][w] > max_score) {
						max_score = data[h][w];
						max_x = w;
						max_y = h;
					}
				}
			}
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
