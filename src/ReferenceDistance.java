import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
		//double x_min = 317900;
		//double x_max = 317998;
		//double y_min = 4367500;
		//double y_max = 4367815;

		int rows;

		try {

			calculateReferencePoint(input_file_name);
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
				data[i][0] = distance(max_x, max_y, x_double, y_double); // (top left)

			}

			File exlFile = new File(output_file_name);
			WritableWorkbook writableWorkbook = Workbook.createWorkbook(exlFile);

			WritableSheet writableSheet = writableWorkbook.createSheet("4 Days", 0);

			Label ref1 = new Label (0, 0, "Top Left");

			writableSheet.addCell(ref1);

			for (int i = 1; i < rows/4; i++){
				Number value1 = new Number (0, i , data[i][0]);

				writableSheet.addCell(value1);
			}	


			int a = 1;
			for (int i = rows/4; i < rows/2; i++){
				Number value1 = new Number (1, a , data[i][0]);

				writableSheet.addCell(value1);
				a++;
			}

			a = 1;
			for (int i = rows/2; i < 3*rows/4; i++){
				Number value1 = new Number (2, a , data[i][0]);

				writableSheet.addCell(value1);
				a++;
			}

			a=1;
			for (int i = 3*rows/4; i < rows; i++){
				Number value1 = new Number (3, a , data[i][0]);

				writableSheet.addCell(value1);
				a++;
			}

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
			System.out.println("started");
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

				Double x_cell_Double = Double.parseDouble(x_cell_string);
				Double y_cell_Double = Double.parseDouble(y_cell_string);

				int x_int = x_cell_Double.intValue();
				int y_int = y_cell_Double.intValue();

				int x_loc = x_int - 317901;
				int y_loc = y_int - 4367501;

				if (x_loc < 99 && y_loc < 316) {
					data[y_loc][x_loc]++;
				}
				else {
					System.out.println("Data is not in valid length. x: " + x_loc + ". y: " + y_loc);
				}
			}
			System.out.println("middle");
			int max_score = 0;
			

			String out_file_name = input_file_name.substring(4, input_file_name.length()-4);
			out_file_name = "reference/" + out_file_name + ".txt";
			System.out.println(out_file_name);

			File file = new File(out_file_name);
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			int wide = -1;
			int hite = -1;

			for (int h = 0; h < 315; h++) {
				for (int w = 0; w < 98; w++) {
					if (data[h][w] > 1)
						bw.write("\t" + data[h][w]);
					else
						bw.write("\t");
					if (data[h][w] > max_score) {
						max_score = data[h][w];
						max_x = w + 317901;
						max_y = h + 4367501;
						wide = w;
						hite = h;
					}
				}
				bw.newLine();
			}
			
			bw.write("Max X: " + wide);
			bw.newLine();
			bw.write("Max Y: " + hite);
			bw.newLine();
			bw.write("Occurence#:" + max_score);
			
			bw.close();

			System.out.println("ended");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
