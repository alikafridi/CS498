/* BasicJPanel.java
  This is a somewhat more sophisticated drawing program.
  It uses a new child of JPanel as the drawing surface
  for a JFrame, to avoid the problems with drawing
  directly on a JFrame.

  A custom JPanel child class called BasicJPanel is created
  with its own paintComponent method, which contains our
  drawing code.

  A generic JFrame is then created to hold the BasicJPanel
  object, the BasicJPanel is created, made into the JPanel's
  content pane, and our paintComponent method is called
  automatically. *Whew!*
  mag-28Apr2008
 */

// Import the basic graphics classes.
import java.awt.*;

import javax.swing.*;

public class WindowPanel extends JPanel{

	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		g.setColor(Color.BLUE);
		g.fillOval(10, 10, 10, 10);
		
	}
	
}
