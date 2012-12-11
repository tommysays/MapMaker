import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialogue box for creating warp destinations.
 * @author Tommy
 */
@SuppressWarnings("serial")
public class WarpD extends JFrame implements ActionListener{
	private JPanel pnl;
	private JTextField nameField;
	private JTextField locField;
	WarpD(){
		pnl = new JPanel();
//		
		pnl.setBackground(new Color(230,230,255));
		pnl.setLayout(null);
		addLabels();
		addInput();
		addButtons();
		add(pnl);
		setBounds(300,200,220,185);
		setUndecorated(true);
		toFront();
		setVisible(true);
	}
	/**
	 * Adds labels/text to panel.
	 */
	private void addLabels(){
		JLabel prompt = new JLabel("Enter information about destination.");
		JLabel nameLbl = new JLabel("File name (\"aFile.map\"):");
		JLabel locationLbl = new JLabel("Location to warp to: (\"x,y\")");
		prompt.setBounds(10, 10, 250, 15);
		nameLbl.setBounds(10, 40, 200, 15);
		locationLbl.setBounds(10, 90, 200, 15);
		pnl.add(prompt);
		pnl.add(nameLbl);
		pnl.add(locationLbl);
	}
	/**
	 * Add text fields (for input) to panel.
	 */
	private void addInput(){
		nameField = new JTextField();
		locField = new JTextField();
		nameField.setBounds(10, 60, 200, 20);
		locField.setBounds(10, 110, 200, 20);
		pnl.add(nameField);
		pnl.add(locField);
	}
	/**
	 * Adds "Accept" and "Cancel" buttons to panel.
	 */
	private void addButtons(){
		JButton acceptBtn = new JButton("Accept");
		JButton cancelBtn = new JButton("Cancel");
		acceptBtn.setBounds(15,145,90,25);
		cancelBtn.setBounds(115,145,90,25);
		acceptBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		acceptBtn.setActionCommand("Accept");
		cancelBtn.setActionCommand("Cancel");
		pnl.add(acceptBtn);
		pnl.add(cancelBtn);
	}
	/**
	 * Checks to see if input in location text field is formatted correctly.
	 * @return false if incorrect format, true otherwise;
	 */
	private boolean checkLocField(){
		//1. Check size; 2. Find comma; 3. Only numbers precede it; 4. Only numbers follow.
		String input = locField.getText();
		//1. Must be at least length 3.
		if (input.length() < 3){
			return false;
		}
		//2. Find comma:
		int commaIndex = -1;
		for (int index = 0; index < input.length(); ++index){
			if (input.charAt(index) == ','){
				commaIndex = index;
			}
		}
		//Comma index cannot be the first or last element.
		if (commaIndex < 1 || commaIndex == input.length()){
			return false;
		}
		//3 & 4. Checking preceding and following chars.
		for (int index = 0; index < commaIndex; ++index){
			if (!Character.isDigit(input.charAt(index))){
				return false;
			}
		}
		for (int index = commaIndex + 1; index < input.length(); ++index){
			if (!Character.isDigit(input.charAt(index))){
				return false;
			}
		}
		return true;
	}
	/**
	 * Formats the name field to include ".map", if not already included.
	 */
	private void formatNameField(){
		String input = nameField.getText();
		if (input.length() < 4){
			input += ".map";
			nameField.setText(input);
		} else{
			System.out.println(input);
			if (!input.substring(input.length() - 4).equals(".map")){
				input += ".map";
				nameField.setText(input);
			} else{
				return;
			}
		}
	}
	/**
	 * Converts the input "x,y" in location field to a Point.
	 */
	private Point locToPoint(){
		String input = locField.getText();
		int commaIndex = -1;
		for (int index = 0; index < input.length(); ++index){
			if (input.charAt(index) == ','){
				commaIndex = index;
				break;
			}
		}
		int x = Integer.parseInt(input.substring(0, commaIndex));
		int y = Integer.parseInt(input.substring(commaIndex + 1));
		return new Point(x, y);
	}
	public void actionPerformed(ActionEvent ae) {
		String actionCommand = ae.getActionCommand();
		if (actionCommand.equals("Accept")){
			if (!checkLocField()){
				JOptionPane.showMessageDialog(pnl, "Incorrect location format! Must be \"x,y\", no spaces or quotes.");
				return;
			}
			Point pt = locToPoint();
			if (pt.getX() < 0 || pt.getY() < 0){
				JOptionPane.showMessageDialog(pnl, "Incorrect location format! No negatives allowed.");
				return;
			}
			formatNameField();
			String name = nameField.getText();
			MapMaker.addWarpData(name, pt);
			MapMaker.getFrame().setEnabled(true);
			dispose();
			
		} else if (actionCommand.equals("Cancel")){
			MapMaker.getFrame().setEnabled(true);
			dispose();
		}
	}
	
}
