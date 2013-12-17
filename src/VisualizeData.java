/**
 * @author Ali K. Afridi
 * 
 * Last Modified: 12/07/13
 * 
 * This software creates a map of the livestock's location over time
 * 
 */

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class VisualizeData{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
			
            Workbook wrk1 =  Workbook.getWorkbook(new File("xls/M0126.xls"));
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
}
