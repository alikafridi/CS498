import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class CombineRows {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int days = 4; //days in the data

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Visualizer");

		//String file_name = JOptionPane.showInputDialog(null, "What is the name of the file?");
		int new_count=0;
		int old_count=0;
		int combine_num_rows= 7; //7 or 8 per (every 30 mins)

		try {

			Workbook wrk1 =  Workbook.getWorkbook(new File("xls/M0126.xls"));
			
			Sheet sheet1 = wrk1.getSheet(0);

			while(true){

				/** DAY 1 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell = sheet1.getCell(1, 0); //replace 0 with row num
				Cell y_cell = sheet1.getCell(2, 0); //replace 0 with row num

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string = x_cell.getContents();
				String y_string = y_cell.getContents();

				Double x_double = Double.parseDouble(x_string) - 317900;
				Double y_double = Double.parseDouble(y_string) - 4367500;

				int x_int = x_double.intValue();
				int y_int = y_double.intValue();

				
			}


		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

}
