import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Dialogue box for creating a new Map.
 * @author Tommy
 */
@SuppressWarnings("serial")
public class NewMapD extends JFrame implements ActionListener{
	private JPanel pnl;
	private JComboBox widthBox, heightBox;
	private final int SIZE = 50;
	private int width = 0, height = 0;
	NewMapD(){
		setBounds(300,200,300,200);
		setLayout(null);
		setUndecorated(true);
		toFront();
		setBackground(Color.WHITE);
		pnl = new JPanel();
		pnl.setBounds(0,0,300,200);
		pnl.setLayout(null);
		addButtons();
		addWidthBox();
		addHeightBox();
		addLabels();
		add(pnl);
		setVisible(true);
	}
	/**
	 * Adds "Accept" and "Cancel" buttons to panel.
	 */
	private void addButtons(){
		JButton accept = new JButton("Accept");
		JButton cancel = new JButton("Cancel");
		accept.setBounds(40,100,75,25);
		cancel.setBounds(135,100,75,25);
		accept.addActionListener(this);
		cancel.addActionListener(this);
		accept.setActionCommand("Accept");
		cancel.setActionCommand("Cancel");
		pnl.add(accept);
		pnl.add(cancel);
	}
	/**
	 * Adds labels/text to panel.
	 */
	private void addLabels(){
		JLabel prompt = new JLabel("Select the height and width of the new map:");
		JLabel widthLbl = new JLabel("Width:");
		JLabel heightLbl = new JLabel("Height:");
		prompt.setBounds(20,25,300,25);
		widthLbl.setBounds(20,65,75,25);
		heightLbl.setBounds(135,65,75,25);
		pnl.add(prompt);
		pnl.add(widthLbl);
		pnl.add(heightLbl);
	}
	/**
	 * Creates the width combo box and adds to panel.
	 */
	private void addWidthBox(){
		String[] str = new String[SIZE];
		for (int x = 0; x < SIZE; ++x){
			str[x] = "" + (x+1);
		}
		widthBox = new JComboBox(str);
		widthBox.setBounds(70,65,45,20);
		widthBox.addActionListener(this);
		pnl.add(widthBox);
	}
	/**
	 * Creates the height combo box and adds to panel.
	 */
	private void addHeightBox(){
		String[] str = new String[SIZE];
		for (int x = 0; x < SIZE; ++x){
			str[x] = "" + (x+1);
		}
		heightBox = new JComboBox(str);
		heightBox.setBounds(180,65,45,20);
		heightBox.addActionListener(this);
		pnl.add(heightBox);
	}
	public void actionPerformed(ActionEvent ae) {
		String actionCommand = ae.getActionCommand();
		if (actionCommand.equals("Accept")){
			JComboBox bx = widthBox;
			setWidth(bx.getSelectedIndex() + 1);
			bx = heightBox;
			setHeight(bx.getSelectedIndex() + 1);
			MapMaker.getFrame().setEnabled(true);
			MapMaker.setNewMapDimensions(width, height);
			dispose();
		} else if (actionCommand.equals("Cancel")){
			MapMaker.getFrame().setEnabled(true);
			dispose();
		}
		repaint();
	}
	private void setWidth(int x){
		width = x;
	}
	private void setHeight(int y){
		height = y;
	}
}
