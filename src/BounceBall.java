import java.awt.Color;

import javax.swing.JFrame;

public class BounceBall {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int days = 4;
		int width = days*200;

		

		JFrame frame=new JFrame("CS 498 Data");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JCanvas canvas=new JCanvas();
		frame.add(canvas);
		frame.setVisible(true);

		int vx=8,vy=10,x=0,y=50,d=50;
		while(true){
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
			canvas.endBuffer();
			canvas.sleep(20);
		}
	}
}