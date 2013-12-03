import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class VisualizeData{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int days = 4; //days in the data
		int frame_width = days*200;
		int frame_height = 480;

		JOptionPane.showMessageDialog(null, "Ali Afridi \nCS 498 - Data Visualizer");
		
		String file_name = JOptionPane.showInputDialog(null, "What is the name of the file?");
		
		
		
		/*

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
		}*/
	}
}
