import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Frame that appears when mouse over warp choices. Displays info regarding warp destination.
 * @author Tommy
 */
@SuppressWarnings("serial")
public class WarpMouseOver extends JFrame{
	private String nameData;
	private Point pointData;
	private int ID;
	private JPanel pnl;
	WarpMouseOver(int x, int y, String name, Point point, int id){
		nameData = name;
		pointData = point;
		ID = id;
		pnl = new JPanel();
		pnl.setBackground(new Color(220, 240, 200));
		addLabels();
		add(pnl);
		setUndecorated(true);
		toFront();
		setFocusable(false);
		setVisible(true);
	}
	public void move(int x, int y){
		setBounds(x, y, 250, 65);
		toFront();
	}
	public int getID(){
		return ID;
	}
	private void addLabels(){
		pnl.setLayout(null);
		JLabel name = new JLabel("Name: " + nameData);
		JLabel point = new JLabel("Destination: " + (int)pointData.getX()
				+ "," + (int)pointData.getY());
		JLabel id = new JLabel("ID: " + ID);
		id.setBounds(10,10,240,15);
		name.setBounds(10,25,240,15);
		point.setBounds(10,40,240,15);
		pnl.add(id);
		pnl.add(name);
		pnl.add(point);
	}
}
