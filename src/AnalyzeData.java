import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 
 */

/**
 * @author alikafridi
 *
 */
public class AnalyzeData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int days = 4; //days in the data
		int frame_width = 800;
		int frame_height = 480;

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Analyzer");
		
		//String file_name = JOptionPane.showInputDialog(null, "What is the name of the file?");

		JFrame frame=new JFrame("CS 498 Data Analyzer");
		frame.setSize(frame_width,frame_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCanvas canvas=new JCanvas();
		frame.add(canvas);
		frame.setVisible(true);
		
		try {
            
            //Create a workbook object from <span id="IL_AD5" class="IL_AD">the file</span> at specified location.
            //Change the path of the file as per the location on your computer.
			//FileInputStream file = new FileInputStream(new File("M0110.xlsx"));
			//Get the workbook instance for XLS file 
			//HSSFWorkbook workbook = new HSSFWorkbook(file);
			
			//Get first sheet from the workbook
			//HSSFSheet sheet = workbook.getSheetAt(0);
			
            Workbook wrk1 =  Workbook.getWorkbook(new File("xls/M0126.xls"));
            
            //Obtain the reference to the first sheet in the workbook
            Sheet sheet1 = wrk1.getSheet(0);
            
 
             
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

		
		
	}

}
