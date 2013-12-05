import java.awt.Color;
import java.io.IOException;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jxl.*; 
import jxl.read.biff.BiffException; 
import java.io.File; 
import java.io.IOException; 


public class VisualizeData{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int days = 4; //days in the data
		int frame_width = days*200;
		int frame_height = 480;

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Visualizer");
		
		//String file_name = JOptionPane.showInputDialog(null, "What is the name of the file?");
		
		try {
            
            //Create a workbook object from <span id="IL_AD5" class="IL_AD">the file</span> at specified location.
            //Change the path of the file as per the location on your computer.
            Workbook wrk1 =  Workbook.getWorkbook(new File("write_test.xls"));
            
            //Obtain the reference to the first sheet in the workbook
            Sheet sheet1 = wrk1.getSheet(0);

            //Obtain reference to the Cell using getCell(int col, int row) method of sheet
            Cell colArow1 = sheet1.getCell(0, 0);
            Cell colArow2 = sheet1.getCell(1, 0);
             
            //Read the contents of the Cell using getContents() method, which will return
            //it as a String
            String str_colArow1 = colArow1.getContents();
            String str_colArow2 = colArow2.getContents();
             
            //Display the cell contents
            System.out.println("Contents of cell Col A Row 1: \""+str_colArow1 + "\"");
            System.out.println("Contents of cell Col A Row 2: \""+str_colArow2 + "\"");
 
             
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

		JFrame frame=new JFrame("CS 498 Data");
		frame.setSize(frame_width,frame_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCanvas canvas=new JCanvas();
		frame.add(canvas);
		frame.setVisible(true);

		int vx=2,vy=2,x=0,y=50,d=10;
		int vMin = 4, dMin = 0;
		
		while(dMin < 60*24){
			int mx=canvas.getWidth();
			int my=canvas.getHeight();
			canvas.startBuffer();
			canvas.clear();
			
			canvas.setPaint(Color.green);
			
			x+=vx; y+=vy;
			
			if(x<0){x=0;vx=-vx;}
			if(y<0){y=0;vy=-vy;}
			if(x+d>mx){x=mx-d;vx=-vx;}
			if(y+d>my){y=my-d;vy=-vy;}
			
			canvas.fillOval(x,y,d,d);
			
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
			
			canvas.endBuffer();
			canvas.sleep(50);
		}
	}
}
