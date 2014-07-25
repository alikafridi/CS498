/**
 * @author Ali K. Afridi
 * 
 * Last Modified: 5/25/14
 * 
 * This software creates a map of the livestock's location over time
 * 
 */

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class VisualizeData{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Give options
		// How many cows to display
		JFrame frame=new JFrame("CS 498 Data");
		Object[] options = {"All", "Just One"};
		int numOfCows = JOptionPane.showOptionDialog(frame,
				"How many cows would you like to display?",
				"Visualizer",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				null);

		if (numOfCows == 0) // All 
		{
			allVisualizer();
		}
		else if (numOfCows == 1) // Just One
		{
			// Need to implement
			// Select trailing edges
			Object[] trail_options = {"No", "Yes, with a density trail", "Yes, with time trail"};
			int trail = JOptionPane.showOptionDialog(frame,
					"Would you like trailing edges?",
					"Visualizer",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					trail_options,
					null);

			// Select file
			Object[] file_options = {"M0110", "M0126", "M0138",
					"M0153", "M0154", "M0161", 
					"M0162", "M0163"};
			String file_name = (String)JOptionPane.showInputDialog(
					frame,
					"Which file would you like to use?",
					"Visualizer",
					JOptionPane.PLAIN_MESSAGE,
					null,
					file_options,
					null);
			file_name = "xls/" + file_name + ".xls";

			if (trail == 0)
				simplestVisualizer(file_name);
			else if (trail == 1)
				oldVisualizer(file_name);
			else if (trail == 2)
				timeVisualizer(file_name);// TODO
			else
				System.exit(0);
		}
		else
			System.exit(0);
	}

	public static void oldVisualizer(String file_name) {

		int days = 4; //days in the data
		int frame_width = 800;
		int frame_height = 480;

		JFrame frame=new JFrame("CS 498 Data");
		frame.setSize(frame_width,frame_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCanvas canvas=new JCanvas();
		frame.add(canvas);
		frame.setVisible(true);

		int vx=2,vy=2,x=0,y=50,d=10;
		int vMin = 4, dMin = 0;
		int row = 1;
		int rows_in_days = 15*24;


		// To Store data for trailing edges (old movements)
		int [][] data1 = new int[800][480];
		int [][] data2 = new int[800][480];
		int [][] data3 = new int[800][480];
		int [][] data4 = new int[800][480];

		// To Record which x, and which y, values have been visited
		ArrayList<Integer> data1_x = new ArrayList<Integer>();
		ArrayList<Integer> data1_y = new ArrayList<Integer>();
		ArrayList<Integer> data2_x = new ArrayList<Integer>();
		ArrayList<Integer> data2_y = new ArrayList<Integer>();
		ArrayList<Integer> data3_x = new ArrayList<Integer>();
		ArrayList<Integer> data3_y = new ArrayList<Integer>();
		ArrayList<Integer> data4_x = new ArrayList<Integer>();
		ArrayList<Integer> data4_y = new ArrayList<Integer>();


		for (int h = 0; h < 800; h++) {
			for (int w = 0; w < 480; w++) {
				data1[h][w] = 0;
				data2[h][w] = 0;
				data3[h][w] = 0;
				data4[h][w] = 0;
			}
		}

		try {

			//Create a workbook object from <span id="IL_AD5" class="IL_AD">the file</span> at specified location.
			//Change the path of the file as per the location on your computer.
			//FileInputStream file = new FileInputStream(new File("M0110.xlsx"));
			//Get the workbook instance for XLS file 
			//HSSFWorkbook workbook = new HSSFWorkbook(file);

			//Get first sheet from the workbook
			//HSSFSheet sheet = workbook.getSheetAt(0);

			Workbook wrk1 =  Workbook.getWorkbook(new File(file_name));
			// Workbook wrk1 =  Workbook.getWorkbook(new File("movement_data.xls"));


			//Obtain the reference to the first sheet in the workbook
			Sheet sheet1 = wrk1.getSheet(0);

			while(dMin < 60*24){
				canvas.startBuffer();
				canvas.clear();
				//canvas.setBackground(Color.black);

				canvas.setPaint(Color.green);

				/** DAY 1 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell = sheet1.getCell(1, row);
				Cell y_cell = sheet1.getCell(2, row);

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string = x_cell.getContents();
				String y_string = y_cell.getContents();

				Double x_double = Double.parseDouble(x_string) - 317900;
				Double y_double = Double.parseDouble(y_string) - 4367500;

				int x_int = x_double.intValue();
				int y_int = y_double.intValue();

				data1[y_int][x_int]++;
				data1_y.add(y_int);
				data1_x.add(x_int);
				int y_item;
				int x_item;

				for (int i = 0; i < data1_y.size(); i++) {
					y_item = data1_y.get(i);
					x_item = data1_x.get(i);

					Color test = new Color(0, 255, 255, 50);

					canvas.setPaint(test);

					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);
				canvas.fillOval(x_int, y_int,d,d);

				/** DAY 2 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell2 = sheet1.getCell(1, row+rows_in_days);
				Cell y_cell2 = sheet1.getCell(2, row+rows_in_days);

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string2 = x_cell2.getContents();
				String y_string2 = y_cell2.getContents();

				Double x_double2 = Double.parseDouble(x_string2) - 317900;
				Double y_double2 = Double.parseDouble(y_string2) - 4367500;

				int x_int2 = x_double2.intValue()+200;
				int y_int2 = y_double2.intValue();

				data2[y_int2][x_int2-200]++;
				data2_y.add(y_int2);
				data2_x.add(x_int2-200);

				for (int i = 0; i < data2_y.size(); i++) {
					y_item = data2_y.get(i);
					x_item = data2_x.get(i)+200;

					Color test = new Color(0, 255, 255, 50);

					canvas.setPaint(test);

					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);

				canvas.fillOval(x_int2, y_int2,d,d);

				/** DAY 3 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell3 = sheet1.getCell(1, row+(rows_in_days*2));
				Cell y_cell3 = sheet1.getCell(2, row+(rows_in_days*2));

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string3 = x_cell3.getContents();
				String y_string3 = y_cell3.getContents();

				Double x_double3 = Double.parseDouble(x_string3) - 317900;
				Double y_double3 = Double.parseDouble(y_string3) - 4367500;

				int x_int3 = x_double3.intValue()+400;
				int y_int3 = y_double3.intValue();

				data3[y_int3][x_int3-400]++;
				data3_y.add(y_int3);
				data3_x.add(x_int3-400);

				for (int i = 0; i < data3_y.size(); i++) {
					y_item = data3_y.get(i);
					x_item = data3_x.get(i)+400;

					Color test = new Color(0, 255, 255, 50);

					canvas.setPaint(test);
					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);

				canvas.fillOval(x_int3, y_int3,d,d);

				/** DAY 4 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell4 = sheet1.getCell(1, row+(rows_in_days*3));
				Cell y_cell4 = sheet1.getCell(2, row+(rows_in_days*3));

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string4 = x_cell4.getContents();
				String y_string4 = y_cell4.getContents();

				Double x_double4 = Double.parseDouble(x_string4) - 317900;
				Double y_double4 = Double.parseDouble(y_string4) - 4367500;

				int x_int4 = x_double4.intValue()+600;
				int y_int4 = y_double4.intValue();

				data4[y_int4][x_int4-600]++;
				data4_y.add(y_int4);
				data4_x.add(x_int4-600);

				for (int i = 0; i < data4_y.size(); i++) {
					y_item = data4_y.get(i);
					x_item = data4_x.get(i) + 600;

					Color test = new Color(0, 255, 255, 50);

					canvas.setPaint(test);
					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);

				canvas.fillOval(x_int4, y_int4,d,d);

				canvas.setPaint(Color.red);
				canvas.drawString("Day 1", 85, 420);
				canvas.drawString("Day 2", 285, 420);
				canvas.drawString("Day 3", 485, 420);
				canvas.drawString("Day 4", 685, 420);

				canvas.setPaint(Color.black);
				canvas.drawLine(200, 0, 200, 430);
				canvas.drawLine(400, 0, 400, 430);
				canvas.drawLine(600, 0, 600, 430);

				canvas.drawLine(0, 400, 800, 400);
				canvas.drawLine(0, 430, 800, 430);

				dMin += vMin;

				canvas.drawString("Time: " + (dMin/60)%24 + ":" + dMin%60, 360, 450);

				row++;
				canvas.endBuffer();
				canvas.sleep(50);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// The Simplest Visualizer displays one cow with no trailing line
	public static void simplestVisualizer(String file_name) {

		int days = 4; //days in the data
		int frame_width = 800;
		int frame_height = 480;

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Visualizer");

		//String file_name = JOptionPane.showInputDialog(null, "What is the name of the file?");

		JFrame frame=new JFrame("CS 498 Data");
		frame.setSize(frame_width,frame_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCanvas canvas=new JCanvas();
		frame.add(canvas);
		frame.setVisible(true);

		int vx=2,vy=2,x=0,y=50,d=10;
		int vMin = 4, dMin = 0;
		int row = 1;
		int rows_in_days = 15*24;

		try {

			//Create a workbook object from <span id="IL_AD5" class="IL_AD">the file</span> at specified location.
			//Change the path of the file as per the location on your computer.
			//FileInputStream file = new FileInputStream(new File("M0110.xlsx"));
			//Get the workbook instance for XLS file 
			//HSSFWorkbook workbook = new HSSFWorkbook(file);

			//Get first sheet from the workbook
			//HSSFSheet sheet = workbook.getSheetAt(0);

			Workbook wrk1 =  Workbook.getWorkbook(new File(file_name));
			//Workbook wrk1 =  Workbook.getWorkbook(new File("movement_data.xls"));

			//Obtain the reference to the first sheet in the workbook
			Sheet sheet1 = wrk1.getSheet(0);

			while(dMin < 60*24){
				canvas.startBuffer();
				canvas.clear();

				canvas.setPaint(Color.green);

				/** DAY 1 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell = sheet1.getCell(1, row);
				Cell y_cell = sheet1.getCell(2, row);

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string = x_cell.getContents();
				String y_string = y_cell.getContents();

				Double x_double = Double.parseDouble(x_string) - 317900;
				Double y_double = Double.parseDouble(y_string) - 4367500;

				int x_int = x_double.intValue();
				int y_int = y_double.intValue();

				canvas.fillOval(x_int, y_int,d,d);

				/** DAY 2 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell2 = sheet1.getCell(1, row+rows_in_days);
				Cell y_cell2 = sheet1.getCell(2, row+rows_in_days);

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string2 = x_cell2.getContents();
				String y_string2 = y_cell2.getContents();

				Double x_double2 = Double.parseDouble(x_string2) - 317900;
				Double y_double2 = Double.parseDouble(y_string2) - 4367500;

				int x_int2 = x_double2.intValue()+200;
				int y_int2 = y_double2.intValue();

				canvas.fillOval(x_int2, y_int2,d,d);

				/** DAY 3 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell3 = sheet1.getCell(1, row+(rows_in_days*2));
				Cell y_cell3 = sheet1.getCell(2, row+(rows_in_days*2));

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string3 = x_cell3.getContents();
				String y_string3 = y_cell3.getContents();

				Double x_double3 = Double.parseDouble(x_string3) - 317900;
				Double y_double3 = Double.parseDouble(y_string3) - 4367500;

				int x_int3 = x_double3.intValue()+400;
				int y_int3 = y_double3.intValue();

				canvas.fillOval(x_int3, y_int3,d,d);

				/** DAY 4 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell4 = sheet1.getCell(1, row+(rows_in_days*3));
				Cell y_cell4 = sheet1.getCell(2, row+(rows_in_days*3));

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string4 = x_cell4.getContents();
				String y_string4 = y_cell4.getContents();

				Double x_double4 = Double.parseDouble(x_string4) - 317900;
				Double y_double4 = Double.parseDouble(y_string4) - 4367500;

				int x_int4 = x_double4.intValue()+600;
				int y_int4 = y_double4.intValue();

				canvas.fillOval(x_int4, y_int4,d,d);

				canvas.setPaint(Color.red);
				canvas.drawString("Day 1", 85, 420);
				canvas.drawString("Day 2", 285, 420);
				canvas.drawString("Day 3", 485, 420);
				canvas.drawString("Day 4", 685, 420);

				canvas.setPaint(Color.black);
				canvas.drawLine(200, 0, 200, 430);
				canvas.drawLine(400, 0, 400, 430);
				canvas.drawLine(600, 0, 600, 430);

				canvas.drawLine(0, 400, 800, 400);
				canvas.drawLine(0, 430, 800, 430);



				dMin += vMin;

				canvas.drawString("Time: " + (dMin/60)%24 + ":" + dMin%60, 360, 450);

				row++;
				canvas.endBuffer();
				canvas.sleep(50);
			}


		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void timeVisualizer(String file_name) {

		int degrade_rate = 5;

		int days = 4; //days in the data
		int frame_width = 800;
		int frame_height = 480;

		JFrame frame=new JFrame("CS 498 Data");
		frame.setSize(frame_width,frame_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCanvas canvas=new JCanvas();
		frame.add(canvas);
		frame.setVisible(true);

		int vx=2,vy=2,x=0,y=50,d=10;
		int vMin = 4, dMin = 0;
		int row = 1;
		int rows_in_days = 15*24;


		// To Store data for trailing edges (old movements)
		int [][] data1 = new int[800][480];
		int [][] data2 = new int[800][480];
		int [][] data3 = new int[800][480];
		int [][] data4 = new int[800][480];

		// To Record which x, and which y, values have been visited
		ArrayList<Integer> data1_x = new ArrayList<Integer>();
		ArrayList<Integer> data1_y = new ArrayList<Integer>();
		ArrayList<Integer> data2_x = new ArrayList<Integer>();
		ArrayList<Integer> data2_y = new ArrayList<Integer>();
		ArrayList<Integer> data3_x = new ArrayList<Integer>();
		ArrayList<Integer> data3_y = new ArrayList<Integer>();
		ArrayList<Integer> data4_x = new ArrayList<Integer>();
		ArrayList<Integer> data4_y = new ArrayList<Integer>();


		for (int h = 0; h < 800; h++) {
			for (int w = 0; w < 480; w++) {
				data1[h][w] = 0;
				data2[h][w] = 0;
				data3[h][w] = 0;
				data4[h][w] = 0;
			}
		}

		try {

			//Create a workbook object from <span id="IL_AD5" class="IL_AD">the file</span> at specified location.
			//Change the path of the file as per the location on your computer.
			//FileInputStream file = new FileInputStream(new File("M0110.xlsx"));
			//Get the workbook instance for XLS file 
			//HSSFWorkbook workbook = new HSSFWorkbook(file);

			//Get first sheet from the workbook
			//HSSFSheet sheet = workbook.getSheetAt(0);

			Workbook wrk1 =  Workbook.getWorkbook(new File(file_name));
			// Workbook wrk1 =  Workbook.getWorkbook(new File("movement_data.xls"));


			//Obtain the reference to the first sheet in the workbook
			Sheet sheet1 = wrk1.getSheet(0);

			while(dMin < 60*24){
				canvas.startBuffer();
				canvas.clear();
				//canvas.setBackground(Color.black);

				canvas.setPaint(Color.green);

				/** DAY 1 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell = sheet1.getCell(1, row);
				Cell y_cell = sheet1.getCell(2, row);

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string = x_cell.getContents();
				String y_string = y_cell.getContents();

				Double x_double = Double.parseDouble(x_string) - 317900;
				Double y_double = Double.parseDouble(y_string) - 4367500;

				int x_int = x_double.intValue();
				int y_int = y_double.intValue();

				data1[y_int][x_int]++;
				data1_y.add(y_int);
				data1_x.add(x_int);
				int y_item;
				int x_item;

				int count = 0;
				for (int i = (data1_y.size()-1); i >= 0 ; i--) {
					y_item = data1_y.get(i);
					x_item = data1_x.get(i);


					int alpha = 255-(count*degrade_rate);
					count++;
					if (alpha < 0)
						alpha = 0;

					Color test = new Color(0, 255, 255, alpha);

					canvas.setPaint(test);

					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);
				canvas.fillOval(x_int, y_int,d,d);

				/** DAY 2 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell2 = sheet1.getCell(1, row+rows_in_days);
				Cell y_cell2 = sheet1.getCell(2, row+rows_in_days);

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string2 = x_cell2.getContents();
				String y_string2 = y_cell2.getContents();

				Double x_double2 = Double.parseDouble(x_string2) - 317900;
				Double y_double2 = Double.parseDouble(y_string2) - 4367500;

				int x_int2 = x_double2.intValue()+200;
				int y_int2 = y_double2.intValue();

				data2[y_int2][x_int2-200]++;
				data2_y.add(y_int2);
				data2_x.add(x_int2-200);

				int count2 =0;
				for (int i = (data2_y.size()-1); i >= 0 ; i--) {
					y_item = data2_y.get(i);
					x_item = data2_x.get(i)+200;

					int alpha = 255-(count2*degrade_rate);
					count2++;
					if (alpha < 0)
						alpha = 0;

					Color test = new Color(0, 255, 255, alpha);

					canvas.setPaint(test);

					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);

				canvas.fillOval(x_int2, y_int2,d,d);

				/** DAY 3 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell3 = sheet1.getCell(1, row+(rows_in_days*2));
				Cell y_cell3 = sheet1.getCell(2, row+(rows_in_days*2));

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string3 = x_cell3.getContents();
				String y_string3 = y_cell3.getContents();

				Double x_double3 = Double.parseDouble(x_string3) - 317900;
				Double y_double3 = Double.parseDouble(y_string3) - 4367500;

				int x_int3 = x_double3.intValue()+400;
				int y_int3 = y_double3.intValue();

				data3[y_int3][x_int3-400]++;
				data3_y.add(y_int3);
				data3_x.add(x_int3-400);

				int count3 = 0;
				for (int i = (data3_y.size()-1); i >= 0 ; i--) {
					y_item = data3_y.get(i);
					x_item = data3_x.get(i)+400;

					int alpha = 255-(count3*degrade_rate);
					count3++;
					if (alpha < 0)
						alpha = 0;

					Color test = new Color(0, 255, 255, alpha);

					canvas.setPaint(test);
					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);

				canvas.fillOval(x_int3, y_int3,d,d);

				/** DAY 4 **/
				//Obtain reference to the Cell using getCell(int col, int row) method of sheet
				Cell x_cell4 = sheet1.getCell(1, row+(rows_in_days*3));
				Cell y_cell4 = sheet1.getCell(2, row+(rows_in_days*3));

				//Read the contents of the Cell using getContents() method, which will return
				//it as a String
				String x_string4 = x_cell4.getContents();
				String y_string4 = y_cell4.getContents();

				Double x_double4 = Double.parseDouble(x_string4) - 317900;
				Double y_double4 = Double.parseDouble(y_string4) - 4367500;

				int x_int4 = x_double4.intValue()+600;
				int y_int4 = y_double4.intValue();

				data4[y_int4][x_int4-600]++;
				data4_y.add(y_int4);
				data4_x.add(x_int4-600);

				int count4 = 0;
				for (int i = (data4_y.size()-1); i >= 0 ; i--) {
					y_item = data4_y.get(i);
					x_item = data4_x.get(i) + 600;

					int alpha = 255-(count4*degrade_rate);
					count4++;
					if (alpha < 0)
						alpha = 0;

					Color test = new Color(0, 255, 255, alpha);

					canvas.setPaint(test);
					canvas.fillOval(x_item, y_item,d,d);
				}
				canvas.setPaint(Color.green);

				canvas.fillOval(x_int4, y_int4,d,d);

				canvas.setPaint(Color.red);
				canvas.drawString("Day 1", 85, 420);
				canvas.drawString("Day 2", 285, 420);
				canvas.drawString("Day 3", 485, 420);
				canvas.drawString("Day 4", 685, 420);

				canvas.setPaint(Color.black);
				canvas.drawLine(200, 0, 200, 430);
				canvas.drawLine(400, 0, 400, 430);
				canvas.drawLine(600, 0, 600, 430);

				canvas.drawLine(0, 400, 800, 400);
				canvas.drawLine(0, 430, 800, 430);

				dMin += vMin;

				canvas.drawString("Time: " + (dMin/60)%24 + ":" + dMin%60, 360, 450);

				row++;
				canvas.endBuffer();
				canvas.sleep(50);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Displays all the cows on the same visualizer
	public static void allVisualizer() {

		int days = 4; //days in the data
		int frame_width = 800;
		int frame_height = 480;

		//JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Visualizer");

		//String file_name = JOptionPane.showInputDialog(null, "What is the name of the file?");

		JFrame frame=new JFrame("CS 498 Data");
		frame.setSize(frame_width,frame_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCanvas canvas=new JCanvas();
		frame.add(canvas);
		frame.setVisible(true);

		int vx=2,vy=2,x=0,y=50,d=10;
		int vMin = 4, dMin = 0;
		int row = 1;
		int rows_in_days = 15*24;

		try {
			//Create a workbook object from <span id="IL_AD5" class="IL_AD">the file</span> at specified location.
			//Change the path of the file as per the location on your computer.
			//FileInputStream file = new FileInputStream(new File("M0110.xlsx"));
			//Get the workbook instance for XLS file 
			//HSSFWorkbook workbook = new HSSFWorkbook(file);

			//Get first sheet from the workbook
			//HSSFSheet sheet = workbook.getSheetAt(0);

			/* XXX
			 * Sheet M0153 and M0162 do not have enough rows for this analysis to work.
			 * The minimum number of rows is 1440
			 */
			
			Workbook [] wrk = new Workbook [6];
			wrk [0] = Workbook.getWorkbook(new File("xls/M0110.xls"));
			wrk [1] = Workbook.getWorkbook(new File("xls/M0126.xls"));
			wrk [2] = Workbook.getWorkbook(new File("xls/M0138.xls"));
			wrk [3] = Workbook.getWorkbook(new File("xls/M0154.xls"));
			wrk [4] = Workbook.getWorkbook(new File("xls/M0161.xls"));
			wrk [5] = Workbook.getWorkbook(new File("xls/M0163.xls"));

			Sheet [] sheets = new Sheet [6];
			Sheet sheet1 = sheets[0];

			for (int i = 0; i < 6; i++) {
				sheets[i] = wrk[i].getSheet(0);
				System.out.println("Sheet" + i + ": " + sheets[i].getRows());
			}
			
			Color [] colors = new Color [6];
			colors[0] = Color.green;
			colors[1] = Color.red;
			colors[2] = Color.blue;
			colors[3] = Color.cyan;
			colors[4] = Color.orange;
			colors[5] = Color.pink;
			
			while(dMin < 60*24){
				canvas.startBuffer();
				canvas.clear();

				for (int i = 0; i < 6; i++){
					sheet1 = sheets[i];
					canvas.setPaint(colors[i]);
					/** DAY 1 **/
					//Obtain reference to the Cell using getCell(int col, int row) method of sheet
					Cell x_cell = sheet1.getCell(1, row);
					Cell y_cell = sheet1.getCell(2, row);

					//Read the contents of the Cell using getContents() method, which will return
					//it as a String
					String x_string = x_cell.getContents();
					String y_string = y_cell.getContents();

					Double x_double = Double.parseDouble(x_string) - 317900;
					Double y_double = Double.parseDouble(y_string) - 4367500;

					int x_int = x_double.intValue();
					int y_int = y_double.intValue();

					canvas.fillOval(x_int, y_int,d,d);

					/** DAY 2 **/
					//Obtain reference to the Cell using getCell(int col, int row) method of sheet
					Cell x_cell2 = sheet1.getCell(1, row+rows_in_days);
					Cell y_cell2 = sheet1.getCell(2, row+rows_in_days);

					//Read the contents of the Cell using getContents() method, which will return
					//it as a String
					String x_string2 = x_cell2.getContents();
					String y_string2 = y_cell2.getContents();

					Double x_double2 = Double.parseDouble(x_string2) - 317900;
					Double y_double2 = Double.parseDouble(y_string2) - 4367500;

					int x_int2 = x_double2.intValue()+200;
					int y_int2 = y_double2.intValue();

					canvas.fillOval(x_int2, y_int2,d,d);

					/** DAY 3 **/
					//Obtain reference to the Cell using getCell(int col, int row) method of sheet
					Cell x_cell3 = sheet1.getCell(1, row+(rows_in_days*2));
					Cell y_cell3 = sheet1.getCell(2, row+(rows_in_days*2));

					//Read the contents of the Cell using getContents() method, which will return
					//it as a String
					String x_string3 = x_cell3.getContents();
					String y_string3 = y_cell3.getContents();

					Double x_double3 = Double.parseDouble(x_string3) - 317900;
					Double y_double3 = Double.parseDouble(y_string3) - 4367500;

					int x_int3 = x_double3.intValue()+400;
					int y_int3 = y_double3.intValue();

					canvas.fillOval(x_int3, y_int3,d,d);

					/** DAY 4 **/
					//Obtain reference to the Cell using getCell(int col, int row) method of sheet
					//System.out.println("File: " + i);
					Cell x_cell4 = sheet1.getCell(1, row+(rows_in_days*3));
					Cell y_cell4 = sheet1.getCell(2, row+(rows_in_days*3));

					//Read the contents of the Cell using getContents() method, which will return
					//it as a String
					String x_string4 = x_cell4.getContents();
					String y_string4 = y_cell4.getContents();

					Double x_double4 = Double.parseDouble(x_string4) - 317900;
					Double y_double4 = Double.parseDouble(y_string4) - 4367500;

					int x_int4 = x_double4.intValue()+600;
					int y_int4 = y_double4.intValue();

					canvas.fillOval(x_int4, y_int4,d,d);
				}

				canvas.setPaint(Color.red);
				canvas.drawString("Day 1", 85, 420);
				canvas.drawString("Day 2", 285, 420);
				canvas.drawString("Day 3", 485, 420);
				canvas.drawString("Day 4", 685, 420);

				canvas.setPaint(Color.black);
				canvas.drawLine(200, 0, 200, 430);
				canvas.drawLine(400, 0, 400, 430);
				canvas.drawLine(600, 0, 600, 430);

				canvas.drawLine(0, 400, 800, 400);
				canvas.drawLine(0, 430, 800, 430);

				dMin += vMin;

				canvas.drawString("Time: " + (dMin/60)%24 + ":" + dMin%60, 360, 450);

				row++;
				canvas.endBuffer();
				canvas.sleep(100);
			}

		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
