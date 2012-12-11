import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JButton;

/**
 * 
 * @author Tommy
 *
 */
public class test extends JFrame{
	public static void main(String[] args){
		test tst = new test();
		tst.setBounds(100, 100, 600, 600);
		tst.setUndecorated(true);
		tst.setVisible(true);
	}
	public void paint(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, 600, 600);
		
		drawAxis(g);
		drawFunction(g);
		
		
		
	}
	public void drawFunction(Graphics g){
		
	}
	
	
	public void drawAxis(Graphics g){
		g.setColor(Color.BLACK);
		g.drawLine(0, 300, 600, 300);
		g.drawLine(300,0,300,600);
	}
	
	public double function(double x){
		return Math.pow(x, 2); //x^2
	}
}



/*
 * Step 1: Make a JFrame.
 * 		-Set Bounds
 * 		-Set Undecorated
 * 		-Set Visible
 * Step 2: Define the "paint(Graphics g)" method.
 * 		-"public void paint(Graphics g)"
 * 		-Use g.something to do something.
 */
