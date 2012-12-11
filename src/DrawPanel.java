import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JPanel;

/**
 * Panel that User draws with/on.
 * @author Tommy
 * @version 1.0 (12/24/11)
 */
@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener{
	private static double[][] baseMap;
	private static double[][] overMap;
	private static double[][] collMap;
	private static int[][] warpMap;
	private static ArrayList<String> warpNames;
	private static ArrayList<Point> warpPoints;
	private static final int TILE_SIZE = 30;
	private static boolean gridOn = false;
	private int xLoc = 0, yLoc = 0;
	private boolean baseIsSet = false;
	private boolean inDrag = false;
	private final int pnlWidth = 600;
	private final int pnlHeight = 594;
	private static HashMap<Double, Image> baseInv;
	private static HashMap<Double, Image> overInv;
	private static HashMap<Double, Image> collInv;
	
	DrawPanel(){
		setBounds(0, 0, pnlWidth, pnlHeight);
		setBackground(new Color(150,150,100));
		addMouseListener(this);
		addMouseMotionListener(this);
		loadAllInv();
	}
	/**
	 * Checks to see if the base tiles have been set. Probably not very useful; should delete soon.
	 * @return a class variable of type boolean, true if set, false otherwise. 
	 */
	public boolean isSet(){
		return baseIsSet;
	}
	public void paint(Graphics g){
		super.paint(g);
		if (baseIsSet){
			int i = baseMap.length, j = baseMap[0].length;
			Graphics gr = g.create(xLoc, yLoc, i * TILE_SIZE, j * TILE_SIZE);
			paintBaseMap(gr);
			paintOverMap(gr);
			if (SelectPanel.getLayer() == 'C'){
				paintCollMap(gr);
			} else if (SelectPanel.getLayer() == 'W'){
				paintWarpMap(gr);
			}
			if (gridOn){
				drawGrid(g, i, j);
			}
		} else{
			g.setFont(new Font("Courier", Font.BOLD, 20));
			g.drawString("To start, click \"File\".", 40, 40);
			g.drawString("Select \"New\" or \"Open\".", 40, 65);
		}
	}
	/**
	 * Draws a grid over the map, used for clarity. Grid appears in red.
	 * @param g : Graphics object to paint to.
	 * @param i : Width of the map, in number of tiles.
	 * @param j : Height of the map, in number of tiles.
	 */
	private void drawGrid(Graphics g, int i, int j){
		for (int x = 0; x < i; ++x){
			g.setColor(Color.RED);
			g.drawLine(xLoc + x * TILE_SIZE, yLoc, xLoc + x * TILE_SIZE, yLoc + j * TILE_SIZE);
		}
		for (int y = 0; y < j; ++y){
			g.setColor(Color.RED);
			g.drawLine(xLoc, yLoc + y * TILE_SIZE, xLoc + i * TILE_SIZE, yLoc + y * TILE_SIZE);
		}
	}
	/**
	 * Paints the base map.
	 * @param g : Graphics object to paint to.
	 */
	private void paintBaseMap(Graphics g){
		//Base tiles are 30x30
		int x = baseMap.length;
		int y = baseMap[0].length;
		for (int i = 0; i < x; ++i){
			for (int j = 0; j < y; ++j){
				Image img = baseInv.get(baseMap[i][j]);
				g.drawImage(img, i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
			}
		}
	}
	/**
	 * Paints the overlay map (fences, curbs, and other such objects).
	 * @param g : Graphics object to paint to.
	 */
	private void paintOverMap(Graphics g){
		//Base tiles are 30x30
		int x = overMap.length;
		int y = overMap[0].length;
		for (int i = 0; i < x; ++i){
			for (int j = 0; j < y; ++j){
				Image img = overInv.get(overMap[i][j]);
				g.drawImage(img, i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
			}
		}
	}
	/**
	 * Paints the collision detection layer of the map. Shows up as checks and x's.
	 * @param g : Graphics object to paint to.
	 */
	private void paintCollMap(Graphics g){
		//Base tiles are 30x30
		int x = collMap.length;
		int y = collMap[0].length;
		for (int i = 0; i < x; ++i){
			for (int j = 0; j < y; ++j){
				Image img = collInv.get(collMap[i][j]);
				g.drawImage(img, i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
			}
		}
	}
	/**
	 * Paints the warp layer of the map. A blue highlight indicates that the tile is set to the
	 * currently selected warp destination, while a yellow highlight indicates that the tile is
	 * set to any other warp destination.
	 * @param g : Graphics object to paint to.
	 */
	private void paintWarpMap(Graphics g){
		int x = warpMap.length;
		int y = warpMap[0].length;
		int selected = (int)MapMaker.getSelectedTile();
		for (int i = 0; i < x; ++i){
			for (int j = 0; j < y; ++j){
				if (warpMap[i][j] != 0){
					if (warpMap[i][j] == selected){
						g.setColor(new Color(100,100,255));
						g.drawRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
						g.setColor(new Color(150,150,255));
						g.drawRect(i * TILE_SIZE + 1, j * TILE_SIZE + 1, TILE_SIZE - 2, TILE_SIZE - 2);
						g.setColor(new Color(200,200,255));
						g.drawRect(i * TILE_SIZE + 2, j * TILE_SIZE + 2, TILE_SIZE - 4, TILE_SIZE - 4);
					} else{
						g.setColor(new Color(255,255,100));
						g.drawRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
						g.setColor(new Color(255,255,150));
						g.drawRect(i * TILE_SIZE + 1, j * TILE_SIZE + 1, TILE_SIZE - 2, TILE_SIZE - 2);
						g.setColor(new Color(255,255,200));
						g.drawRect(i * TILE_SIZE + 2, j * TILE_SIZE + 2, TILE_SIZE - 4, TILE_SIZE - 4);
					}
				}
			}
		}
		
	}
	/**
	 * Grabs the map tile image data from MapMaker.
	 */
	private void loadAllInv(){
		baseInv = MapMaker.getBaseInv();
		overInv = MapMaker.getOverInv();
		collInv = MapMaker.getCollInv();
	}
	/**
	 * Sets the base map.
	 * @param data : <code>double[][]</code> containing the data.
	 */
	public void setBaseData(double[][] data){
		baseMap = data;
		baseIsSet = true;
	}
	/**
	 * Sets the overlay map.
	 * @param data : <code>double[][]</code> containing the data.
	 */
	public void setOverData(double[][] data){
		overMap = data;
	}
	/**
	 * Sets the collision detection map.
	 * @param data : <code>double[][]</code> containing the data.
	 */
	public void setCollData(double[][] data){
		collMap = data;
	}
	/**
	 * Sets the warp map.
	 * @param data : <code>int[][]</code> containing the data.
	 */
	public void setWarpData(int[][] data){
		warpMap = data;
	}
	/**
	 * Sets warp names.
	 * @param data : ArrayList<String> containing the data.
	 */
	public void setWarpNames(ArrayList<String> data){
		warpNames = data;
	}
	/**
	 * Sets warp points/destinations.
	 * @param data : ArrayList<Point> containing the data.
	 */
	public void setWarpPoints(ArrayList<Point> data){
		warpPoints = data;
	}
	/**
	 * Grabs the map tile data from MapMaker. Description not to be confused with loadAllInv(), which grabs image data.
	 */
	public void refreshData(){
		baseMap = MapMaker.getBaseData();
		overMap = MapMaker.getOverData();
		collMap = MapMaker.getCollData();
		warpMap = MapMaker.getWarpData();
		//NOTE: warpNames and warpPoints are not used in this class. Maybe get rid of them/put them elsewhere?
		warpNames = MapMaker.getWarpNames();
		warpPoints = MapMaker.getWarpPoints();
	}
	public double[][] getBaseData(){
		return baseMap;
	}
	public double[][] getOverData(){
		return overMap;
	}
	public double[][] getCollData(){
		return collMap;
	}
	public int[][] getWarpData(){
		return warpMap;
	}
	public ArrayList<String> getWarpNames(){
		return warpNames;
	}
	public ArrayList<Point> getWarpPoints(){
		return warpPoints;
	}
	/**
	 * Checks to see if the subject Point is in the bounds of the map.
	 * @param pt : Point in question.
	 * @return : true if the Point is in bounds, false otherwise.
	 */
	private boolean checkInMap(Point pt){
		if (pt.getX() > xLoc && pt.getX() < (baseMap.length * TILE_SIZE + xLoc)){
			if (pt.getY() > yLoc && pt.getY() < (baseMap[0].length * TILE_SIZE + yLoc)){
				return true;
			} else{
				return false;
			}
		} else{
			return false;
		}
	}
	
	
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	/**
	 * Here lies the code for what to draw, where to draw, and which layer to draw to.
	 */
	public void mousePressed(MouseEvent me){
		if (baseIsSet){
			inDrag = true;
			Point pt = me.getPoint();
			if (checkInMap(pt)){
				int x = (int) (pt.getX() - xLoc) / 30;
				int y = (int) (pt.getY() - yLoc) / 30;

				switch(SelectPanel.getLayer()){
				case 'B':
					if (!MapMaker.sameBaseTile(x,y)){
						MapMaker.changeBaseTile(x, y);
						refreshData();
					}
					break;
				case 'O':
					if (!MapMaker.sameOverTile(x,y)){
						MapMaker.changeOverTile(x, y);
						refreshData();
					}
					break;
				case 'C':
					if (!MapMaker.sameCollTile(x,y)){
						MapMaker.changeCollTile(x, y);
						refreshData();
					}
					break;
				case 'W':
					if (!MapMaker.sameWarpTile(x, y)){
						MapMaker.changeWarpTile(x, y);
						refreshData();
					}
				}
			}
		}
		repaint();
	}
	public void mouseReleased(MouseEvent me){
		inDrag = false;
	}
	/**
	 * Here lies more code on what to draw, where to draw it, and what layer to draw it on.
	 */
	public void mouseDragged(MouseEvent me){
		if (inDrag){
			Point pt = me.getPoint();
			if (checkInMap(pt)){
				int x = (int) (pt.getX() - xLoc) / 30;
				int y = (int) (pt.getY() - yLoc) / 30;
				switch(SelectPanel.getLayer()){
				case 'B':
					if (!MapMaker.sameBaseTile(x,y)){
						MapMaker.changeBaseTile(x, y);
						refreshData();
					}
					break;
				case 'O':
					if (!MapMaker.sameOverTile(x,y)){
						MapMaker.changeOverTile(x, y);
						refreshData();
					}
					break;
				case 'C':
					if (!MapMaker.sameCollTile(x,y)){
						MapMaker.changeCollTile(x, y);
						refreshData();
					}
					break;
				case 'W':
					if (!MapMaker.sameWarpTile(x,y)){
						MapMaker.changeWarpTile(x, y);
						refreshData();
					}
					
				}
			}
		}
		repaint();
	}
	public void mouseMoved(MouseEvent me){}
	/**
	 * Here lies the code for moving the map aroun using WASD or the arrow keys. Movement was written in a gaming perspective, where [down-arrow / 's'] moves the map UP, giving the illusion of moving the character DOWN, and etc.
	 */
	public void keyPressed(KeyEvent ke){
		if (baseIsSet){
			int key = ke.getKeyCode();
			int i = baseMap.length * TILE_SIZE, j = baseMap[0].length * TILE_SIZE;
			switch (key){
			case 87:
			case 38: 
				if (yLoc < pnlHeight - 80){
					yLoc += 30;
				}
				break;
			case 83:
			case 40: 
				if (yLoc > 30 - j){
					yLoc -= 30;
				}
				break;
			case 65:
			case 37:
				if (xLoc < pnlWidth - 30){
					xLoc += 30; 
				}
				break;
			case 68:
			case 39:
				if (xLoc > 30 - i){
					xLoc -= 30;
				}
				break;
			case 71:
				if (gridOn){
					gridOn = false;
				} else{
					gridOn = true;
				}
			}
			repaint();
		}
		
	}
	public void keyReleased(KeyEvent ke){
		
	}
	public void keyTyped(KeyEvent ke){
	} 
}
