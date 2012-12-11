import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Panel that lets User select what layer to work on, and which tile to draw with.
 * @author Tommy
 */
@SuppressWarnings("serial")
public class SelectPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{
	/**
	 * Number of components preceding labels. Is important, don't delete! (It is also
	 * a product of bad coding, but it isn't significant enough of a problem to alter
	 * the structure of this class.
	 */
	private static final int NUM_NON_LBL = 7;
	private static double selectedTile = 0.0;
	private int numberOfChoices = 0;
	private int yLoc = 35;
	private static char layer = 'B';
	private boolean isInsideATile = false;
	private WarpMouseOver info;
	SelectPanel(){
		setLayout(null);
		setBounds(600, 0, 100, 594);
	    setBackground(new Color(100,200,100));
	    addNavButtons();
	    addSwitchButtons();
	    addBaseLabels();
	    addMouseMotionListener(this);
	}
	/**
	 * Part of initialization. Loads the buttons used to switch between layers.
	 */
	private void addSwitchButtons(){
		JButton baseBtn = new JButton("B");
		baseBtn.setBounds(53,80,46,35);
		baseBtn.addActionListener(this);
		baseBtn.setFocusable(false);
		add(baseBtn);
		
		JButton overBtn = new JButton("O");
		overBtn.setBounds(53,120,46,35);
		overBtn.addActionListener(this);
		overBtn.setFocusable(false);
		add(overBtn);
		
		JButton collBtn = new JButton("C");
		collBtn.setBounds(53,160,46,35);
		collBtn.addActionListener(this);
		collBtn.setFocusable(false);
		add(collBtn);
		
		JButton warpBtn = new JButton("W");
		warpBtn.setBounds(53,200,46,35);
		warpBtn.addActionListener(this);
		warpBtn.setFocusable(false);
		add(warpBtn);
	}
	/**
	 * Part of initialization. Loads the buttons used to browse tile types.
	 */
	private void addNavButtons(){
		JButton upBtn = new JButton("Up");
		upBtn.setBounds(0,0,100,25);
		upBtn.addActionListener(this);
		upBtn.setFocusable(false);
		add(upBtn);
		
		JButton downBtn = new JButton("Down");
		downBtn.setBounds(0,546,100,25);
		downBtn.addActionListener(this);
		downBtn.setFocusable(false);
		add(downBtn);
	}
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(new Color(150,50,50));
		g.fillRect(49, 25, 3, 522);
		g.fillRect(51, 75, 49, 3);
	}
	
	/**
	 * Part of initialization, but also used when switching layers. Loads the labels that are used as images for tile selection.
	 */
	private void addBaseLabels(){
		JLabel lbl;
		HashMap<Double, Image> baseInv = MapMaker.getBaseInv();
		numberOfChoices = 0;
		
		//Selected:
		lbl = new JLabel(selectedTile + "");
		lbl.setIcon(new ImageIcon(baseInv.get(selectedTile)));
		lbl.setBounds(60,35,30,30);
		add(lbl);
		
		
		//Tile Choices:
		lbl = new JLabel("0.0");
		lbl.setIcon(new ImageIcon(baseInv.get(0.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc += 40;
		lbl = new JLabel("1.0");
		lbl.setIcon(new ImageIcon(baseInv.get(1.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc += 40;
		lbl = new JLabel("2.0");
		lbl.setIcon(new ImageIcon(baseInv.get(2.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc += 40;
		lbl = new JLabel("3.0");
		lbl.setIcon(new ImageIcon(baseInv.get(3.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc += 40;
		lbl = new JLabel("3.1");
		lbl.setIcon(new ImageIcon(baseInv.get(3.1)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc += 40;
		lbl = new JLabel("4.0");
		lbl.setIcon(new ImageIcon(baseInv.get(4.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;

		yLoc = 35;
	}
	/**
	 * Used when switching layers. Loads the labels that are used as images for tile selection.
	 */
	private void loadOverLabels(){
		JLabel lbl;
		HashMap<Double, Image> overInv = MapMaker.getOverInv();
		numberOfChoices = 0;
		
		//Selected:
		lbl = new JLabel(selectedTile + "");
		lbl.setIcon(new ImageIcon(overInv.get(selectedTile)));
		lbl.setBounds(60,35,30,30);
		add(lbl);
		
		
		//Tile Choices:
		lbl = new JLabel("0.0");
		lbl.setIcon(new ImageIcon(overInv.get(0.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc += 40;
		lbl = new JLabel("1.1");
		lbl.setIcon(new ImageIcon(overInv.get(1.1)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;

		yLoc += 40;
		lbl = new JLabel("1.2");
		lbl.setIcon(new ImageIcon(overInv.get(1.2)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;

		yLoc += 40;
		lbl = new JLabel("1.3");
		lbl.setIcon(new ImageIcon(overInv.get(1.3)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;

		yLoc += 40;
		lbl = new JLabel("1.4");
		lbl.setIcon(new ImageIcon(overInv.get(1.4)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;

		yLoc += 40;
		lbl = new JLabel("1.5");
		lbl.setIcon(new ImageIcon(overInv.get(1.5)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;

		yLoc += 40;
		lbl = new JLabel("1.6");
		lbl.setIcon(new ImageIcon(overInv.get(1.6)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc = 35;
	}
	/**
	 * Used when switching layers. Loads the labels that are used as images for tile selection.
	 */
	private void loadCollLabels(){
		JLabel lbl;
		HashMap<Double, Image> collInv = MapMaker.getCollInv();
		numberOfChoices = 0;
		
		//Selected:
		lbl = new JLabel(selectedTile + "");
		lbl.setIcon(new ImageIcon(collInv.get(selectedTile)));
		lbl.setBounds(60,35,30,30);
		add(lbl);
		
		
		//Tile Choices:
		lbl = new JLabel("0.0");
		lbl.setIcon(new ImageIcon(collInv.get(0.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;
		
		yLoc += 40;
		lbl = new JLabel("1.0");
		lbl.setIcon(new ImageIcon(collInv.get(1.0)));
		lbl.setBounds(10,yLoc,30,30);
		lbl.addMouseListener(this);
		add(lbl);
		numberOfChoices++;

		yLoc = 35;
	}
	private void loadWarpLabels(){
		JLabel lbl;
		ArrayList<String> names = MapMaker.getWarpNames();
		numberOfChoices = 0;
		
		//Selected:
		if (names == null || names.size() < 0){
			lbl = new JLabel("", JLabel.CENTER);
			lbl.setBackground(new Color(240,220,200));
			lbl.setOpaque(true);
		} else{
			lbl = new JLabel((int)selectedTile + "", JLabel.CENTER);
			lbl.setBackground(new Color(240,220,200));
			lbl.setOpaque(true);
		}
		lbl.setBounds(60,35,30,30);
		add(lbl);
		
		//Tile Choices:
//		//Zero:
//		lbl = new JLabel(0 + "", JLabel.CENTER);
//		lbl.setBackground(new Color(240,220,200));
//		lbl.setBounds(10, yLoc, 30, 30);
//		lbl.addMouseListener(this);
//		lbl.addMouseMotionListener(this);
//		add(lbl);
//		yLoc += 40;
		
		//Others:
		for (int index = 0; index < names.size(); ++index){
			lbl = new JLabel(index + "", JLabel.CENTER);
			if (index == (int)selectedTile){
				lbl.setOpaque(true);
			}
			lbl.setBackground(new Color(240,220,200));
			lbl.setBounds(10,yLoc,30,30);
			lbl.addMouseListener(this);
			lbl.addMouseMotionListener(this);
			add(lbl);
			yLoc += 40;
			numberOfChoices++;
		}
		
		//New Warp button:
		JButton btn = new JButton("+");
		btn.setBounds(4,yLoc - 6,42,42);
		btn.addActionListener(this);
		btn.setFocusable(false);
		add(btn);
		yLoc = 35;
	}
	/**
	 * Switches current working layer, depending on which button was pressed.
	 */
	private void switchLayers(){
		selectedTile = 0.0;
		JLabel lbl = (JLabel)getComponent(NUM_NON_LBL - 1);
		switch(layer){
		case 'B':
			lbl.setIcon(new ImageIcon(MapMaker.getBaseInv().get(selectedTile)));
			removeAll();
			addNavButtons();
		    addSwitchButtons();
		    addBaseLabels();
			break;
		case 'O':
			lbl.setIcon(new ImageIcon(MapMaker.getOverInv().get(selectedTile)));
			removeAll();
			addNavButtons();
		    addSwitchButtons();
		    loadOverLabels();
			break;
		case 'C':
			lbl.setIcon(new ImageIcon(MapMaker.getCollInv().get(selectedTile)));
			removeAll();
			addNavButtons();
		    addSwitchButtons();
		    loadCollLabels();
			break;
		case 'W':
			removeAll();
			addNavButtons();
		    addSwitchButtons();
		    loadWarpLabels();
			break;
		}
		selectedTile = 0.0;
		lbl = (JLabel)getComponent(NUM_NON_LBL - 1);
		switch(layer){
		case 'B':
			lbl.setIcon(new ImageIcon(MapMaker.getBaseInv().get(selectedTile)));
			break;
		case 'O':
			lbl.setIcon(new ImageIcon(MapMaker.getOverInv().get(selectedTile)));
			break;
		case 'C':
			lbl.setIcon(new ImageIcon(MapMaker.getCollInv().get(selectedTile)));
			break;
		case 'W':
			if (MapMaker.getWarpNames() == null || MapMaker.getWarpNames().size() < 0){
				lbl.setText("");
			} else{
				lbl.setText((int)selectedTile + "");
			}
			break;
		}
	}
	/**
	 * Used to shift image labels when browsing tile types.
	 */
	private void moveLabels(){
		int total = getComponentCount();
		for (int index = NUM_NON_LBL; index < total; ++index){
			getComponent(index).setBounds(10,yLoc + ((index - NUM_NON_LBL)*40),30,30);
		}
	}
	private void warpHighlight(){
		int total = getComponentCount() - 1;
		for (int index = NUM_NON_LBL; index < total; ++index){
			try{
			JLabel lbl = (JLabel)getComponent(index);
			if (lbl.getText().equals((int)selectedTile + "")){
				lbl.setOpaque(true);
			} else{
				lbl.setOpaque(false);
			}
			} finally{
				
			}
		}
		repaint();
	}
	public void mouseClicked(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	/**
	 * Here lies the code for selecting which layer to work on.
	 */
	public void mousePressed(MouseEvent me) {
		try{
			if (layer == 'B' || layer == 'O' || layer == 'C'){
				selectedTile = Double.parseDouble(((JLabel)me.getComponent()).getText());
				JLabel lbl = (JLabel)getComponent(NUM_NON_LBL - 1);
				switch(layer){
				case 'B':
					lbl.setIcon(new ImageIcon(MapMaker.getBaseInv().get(selectedTile)));
					break;
				case 'O':
					lbl.setIcon(new ImageIcon(MapMaker.getOverInv().get(selectedTile)));
					break;
				case 'C':
					lbl.setIcon(new ImageIcon(MapMaker.getCollInv().get(selectedTile)));
					break;
				}
			} else if (layer == 'W'){
				selectedTile = Integer.parseInt(((JLabel)me.getComponent()).getText());
				JLabel lbl = (JLabel)getComponent(NUM_NON_LBL - 1);
				ArrayList<String> names = MapMaker.getWarpNames();
				if (names == null || names.size() < 0){
					lbl.setText("");
				} else{
					lbl.setText((int)selectedTile + "");
				}
				warpHighlight();
			}
		} catch(Exception e){
			System.err.println("something happened in select panel");
		}
		if (info != null){
			info.toFront();
		}
		MapMaker.getDrawPanel().repaint();
	}
	public void mouseReleased(MouseEvent me) {
		if (info != null){
			info.toFront();
		}
	}
	/**
	 * Here lies the code for switching layers and scrolling tile choices whilst browsing.
	 */
	public void actionPerformed(ActionEvent ae) {
		JButton btn = (JButton)ae.getSource();
		if (btn.getText().equals("Up")){
			if (yLoc < 35){
				yLoc += 40;
				moveLabels();
			}
		} else if (btn.getText().equals("Down")){
			if (yLoc >= 115 - numberOfChoices * 40){
				yLoc -= 40;
				moveLabels();
			}
		} else if (btn.getText().equals("B")){
			if (layer != 'B'){
				layer = 'B';
				switchLayers();
				MapMaker.getDrawPanel().repaint();
			}
		} else if (btn.getText().equals("O")){
			if (layer != 'O'){
				layer = 'O';
				switchLayers();
				MapMaker.getDrawPanel().repaint();
			}
		} else if (btn.getText().equals("C")){
			if (layer != 'C'){
				layer = 'C';
				switchLayers();
				MapMaker.getDrawPanel().repaint();
			}
		} else if (btn.getText().equals("W")){
			if (layer != 'W'){
				layer = 'W';
				switchLayers();
				MapMaker.getDrawPanel().repaint();
			}
		} else if (btn.getText().equals("+")){
			if (MapMaker.getBaseData() != null){
				MapMaker.getFrame().setEnabled(false);
				@SuppressWarnings("unused")
				WarpD dialogue = new WarpD();
			}
		}
		repaint();
	}
	/**
	 * Resets data and tiles to match MapMaker's data.
	 */
	public void refreshData(){
		switchLayers();
		repaint();
	}
	public double getSTile(){
		return selectedTile;
	}
	public static char getLayer(){
		return layer;
	}
	public void mouseDragged(MouseEvent me) {}
	public void mouseMoved(MouseEvent me) {
		if (layer == 'W'){
			Point mousePt = me.getPoint();
			if (me.getComponent().getClass().getCanonicalName().equals("javax.swing.JLabel")){
				mousePt = SwingUtilities.convertPoint(me.getComponent(), mousePt, this);
			}
			double meX = (int)mousePt.getX();
			double meY = (int)mousePt.getY();
			int width = (int)getComponent(NUM_NON_LBL).getWidth();
			int height = (int)getComponent(NUM_NON_LBL).getHeight();
			for (int index = NUM_NON_LBL; index < getComponentCount() - 1; ++index){
				JLabel lbl = (JLabel)getComponent(index);
				int lblX = lbl.getX();
				int lblY = lbl.getY();
				int ID = Integer.parseInt(lbl.getText());
				if (meX > lblX && meX < lblX + width){
					if (meY > lblY && meY < lblY + height){
						Point fromFrame = SwingUtilities.convertPoint(this, mousePt, MapMaker.getFrame());
						double x = fromFrame.getX();
						double y = fromFrame.getY();
						isInsideATile = true;
						String name = MapMaker.getWarpNames().get(ID);
						Point pt = MapMaker.getWarpPoints().get(ID);
						if (info != null){
							if (info.getID() == ID){
								info.move((int)x, (int)y);
							} else{
								info.dispose();
								info = new WarpMouseOver((int)x, (int)y, name, pt, ID);
							}
						} else{
							info = new WarpMouseOver((int)x, (int)y, name, pt, ID);
						}
					}
				}
			}
			if (isInsideATile){
				isInsideATile = false;
			} else{
				if (info != null){
					info.dispose();
					info = null;
				}
			}
		}
	}
}
