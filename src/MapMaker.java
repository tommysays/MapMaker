import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Frame that contains all interactable elements. Mediates between most other
 * classes in this package.
 * @author Tommy
 * @version v1.2 (12/24/11)
 */
@SuppressWarnings("serial")
public class MapMaker extends JFrame implements ActionListener{
	//TODO Implement NPC addition.
	/*
	 * Ideas for NPC implementation:
	 * -SelectPnl has 2 choices : Remove and Add.
	 *  -Remove acts like warp's Erase tile.
	 *  -Add:
	 *  	-Click on a tile in DrawPnl to get dialogue box
	 *  	-Enter NPC Info
	 *  	-Click on tile that has NPC to rotate NPC
	 *  	 -(Right-click rotates other way)
	 */
	private double[][] baseMap;
	private double[][] overMap;
	private double[][] collMap;
	private int[][] warpMap;
	private ArrayList<String> warpNames = new ArrayList<String>();
	private ArrayList<Point> warpPoints = new ArrayList<Point>();
	/**
	 * Any extra data that does not pertain to this step of map design (such
	 * as NPC or Monster data, etc.) will be stored here and appended to the
	 * end of the .map file.
	 */
	private String leftovers;
	private static HashMap<Double, Image> baseInv = new HashMap<Double, Image>();
	private static HashMap<Double, Image> overInv = new HashMap<Double, Image>();
	private static HashMap<Double, Image> collInv = new HashMap<Double, Image>();
	private static final JFileChooser fc = new JFileChooser();
	private static MapMaker mk;
	private static DrawPanel drawPnl;
	private static SelectPanel selectPnl;
	private static final int mkWidth = 700;
	private static final int mkHeight = 594;
	private static int mapWidth, mapHeight;
	public static void main(String[] argimapirate){
		mk = new MapMaker();
		mk.setUndecorated(true);
		mk.setBounds(200, 100, mkWidth, mkHeight);
		mk.setLayout(null);
		mk.setBackground(Color.RED);
		//File Filter for Open/Save
		fc.setFileFilter(new MapFileFilter());
		fc.setAcceptAllFileFilterUsed(false);
		fc.setCurrentDirectory(new File("src"));

		//Menu
		JMenuBar menuBar = new JMenuBar();
		mk.loadMenuBar(menuBar);

		//Select Panel - the panel on the right, containing tile selections.
		mk.loadSelectPnl();

		//Draw Panel - the bigger panel, for placing tiles.
		mk.loadDrawPnl();

		mk.setVisible(true);
	}
	public static JFrame getFrame(){
		return mk;
	}
	private void loadDrawPnl(){
		drawPnl = new DrawPanel();
		mk.addKeyListener(drawPnl);
		add(drawPnl);
	}
	/**
	 * Part of initiailization. Loads the menu bar to the frame.
	 * @param menuBar Instance of JMenuBar to use.
	 */
	private void loadMenuBar(JMenuBar menuBar){
		JMenu mFile = new JMenu("File");
		JMenuItem mNew = new JMenuItem("New");
		mNew.setActionCommand("new");
		JMenuItem mOpen = new JMenuItem("Open");
		mOpen.setActionCommand("open");
		JMenuItem mSave = new JMenuItem("Save");
		mSave.setActionCommand("save");
		JMenuItem mExit = new JMenuItem("Exit");
		mExit.setActionCommand("exit");
		JMenuItem mPrint = new JMenuItem("Print to Console");
		mPrint.setActionCommand("print");
		mNew.addActionListener(this);
		mOpen.addActionListener(this);
		mSave.addActionListener(this);
		mPrint.addActionListener(this);
		mExit.addActionListener(this);
		mFile.add(mNew);
		mFile.add(mOpen);
		mFile.add(mSave);
		mFile.add(mPrint);
		mFile.add(mExit);
		menuBar.add(mFile);
		setJMenuBar(menuBar);
	}
	/**
	 * Part of initialization. Loads images and the SelectPanel instance.
	 */
	private void loadSelectPnl(){
		loadBaseImages();
		loadOverImages();
		loadCollImages();
		selectPnl = new SelectPanel();
		add(selectPnl);
	}
	/**
	 * Part of initialization. Called by loadSelectPnl(), loads base tile
	 * images.
	 */
	private void loadBaseImages(){
		BufferedImage img;
		try{
			img = ImageIO.read(new File("src/baseTiles/black.bmp"));
			baseInv.put(0.0, img);

			img = ImageIO.read(new File("src/baseTiles/grass.bmp"));
			baseInv.put(1.0, img);

			img = ImageIO.read(new File("src/baseTiles/dirt.bmp"));
			baseInv.put(2.0, img);

			img = ImageIO.read(new File("src/baseTiles/brick.bmp"));
			baseInv.put(3.0, img);

			img = ImageIO.read(new File("src/baseTiles/brick2.bmp"));
			baseInv.put(3.1, img);

			img = ImageIO.read(new File("src/baseTiles/water.bmp"));
			baseInv.put(4.0, img);
		} catch(IOException e){
			System.err.println("ERROR: Could not load baseTile images.");
			e.printStackTrace();
		}
	}
	private void loadOverImages(){
		//[.1]: vert, [.2]: hor, [.3]: dl, [.4]: dr, [.5]: ul, [.6]: ur
		BufferedImage img;
		try{
			img = ImageIO.read(new File("src/overTiles/null.png"));
			overInv.put(0.0, img);

			img = ImageIO.read(new File("src/overTiles/vFence.png"));
			overInv.put(1.1, img);

			img = ImageIO.read(new File("src/overTiles/hFence.png"));
			overInv.put(1.2, img);

			img = ImageIO.read(new File("src/overTiles/dlFence.png"));
			overInv.put(1.3, img);

			img = ImageIO.read(new File("src/overTiles/drFence.png"));
			overInv.put(1.4, img);

			img = ImageIO.read(new File("src/overTiles/ulFence.png"));
			overInv.put(1.5, img);

			img = ImageIO.read(new File("src/overTiles/urFence.png"));
			overInv.put(1.6, img);
		} catch(IOException e){
			System.err.println("ERROR: Could not load overTile images");
			e.printStackTrace();
		}
	}
	public static void setMapData1(double[][][] mapData){
		mk.baseMap = mapData[0];
		mk.overMap = mapData[1];
		mk.collMap = mapData[2];
	}
	public static void setMapData2(int[][] mapData){
		mk.warpMap = mapData;
	}
	public static void setMapData3(ArrayList<String> mapData){
		mk.warpNames = mapData;
	}
	public static void setMapData4(ArrayList<Point> mapData){
		mk.warpPoints = mapData;
	}
	/**
	 * Part of initialization. Called by loadSelectPnl(), loads collision
	 * images.
	 */
	private void loadCollImages(){
		BufferedImage img;
		try{
			img = ImageIO.read(new File("src/collTiles/yes.png"));
			collInv.put(0.0, img);

			img = ImageIO.read(new File("src/collTiles/no.png"));
			collInv.put(1.0, img);
		} catch(IOException e){
			System.err.println("ERROR: Could not load collTile images.");
			e.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("exit")){
			System.exit(1);
		} else if (ae.getActionCommand().equals("save")){
			if (baseMap != null){
				int response = fc.showSaveDialog(this);
				if (response == JFileChooser.APPROVE_OPTION){
					try {
						File aFile = fc.getSelectedFile();
						if (FileManager.breakPoint(aFile.getName(), 1, ' ') != -1){
							JOptionPane.showMessageDialog(this, "Map names cannot have spaces!");
						} else{
							if (!FileManager.getExtension(aFile).equals(".map")){
								String str = aFile.getAbsolutePath() + ".map";
								aFile = new File(str);
							}
							String str = "";
							//I know these are poorly named. Will change later. Maybe.
							str += baseMap.length + "," + baseMap[0].length + "\n";
							str += FileManager.dataToString1(baseMap);
							str += FileManager.dataToString1(overMap);
							str += FileManager.dataToString1(collMap);
							str += FileManager.dataToString2(warpMap);
							str += FileManager.dataToString3(warpNames);
							str += FileManager.dataToString4(warpPoints);
							if (leftovers != null){
								str += leftovers;
							}
							FileManager.writeFile(aFile, str);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else{
				JOptionPane.showMessageDialog(this, "Cannot save empty map!");
			}
		} else if (ae.getActionCommand().equals("open")){
			int response = fc.showOpenDialog(this);
			if (response == JFileChooser.APPROVE_OPTION){
				/*
				 * Reading .map file:
				 * 1. Reads the first 3 layers, and chops contents of file, keeps leftovers.
				 * 2. Reads the warp layer's array, chops, keeps leftovers.
				 * 3&4. Reads the warp layer's two ArrayLists, using the same process
				 */
				try{
					String contents = FileManager.readMapData1(FileManager.readFile(fc.getSelectedFile()));
					warpMap = new int[baseMap.length][baseMap[0].length];
					contents = FileManager.readMapData2(contents, warpMap);
					warpNames = new ArrayList<String>();
					contents = FileManager.readMapData3(contents, warpNames);
					warpPoints = new ArrayList<Point>();
					contents = FileManager.readMapData4(contents, warpPoints);
					leftovers = contents;
					updateDrawPanel();
					selectPnl.refreshData();
				} catch(Exception e){
					System.err.println("ERROR: Corrupt file: " + fc.getSelectedFile().getName());
					System.err.println("  " + e.getMessage());
					//TODO remove exit, replace with cleanup.
					System.exit(1);
				}
			}
		} else if (ae.getActionCommand().equals("new")){
			mk.setEnabled(false);
			@SuppressWarnings("unused")
			NewMapD md = new NewMapD();
		} else if (ae.getActionCommand().equals("print")){
			if (baseMap != null){
//				FileManager.printData1(baseMap);
				for (int y = 0; y < baseMap[0].length; ++y){
					for (int x = 0; x < baseMap[0].length; ++x){
						System.out.print((int)(baseMap[x][y]));
					}
					System.out.println("");
				}
//				FileManager.printData2(warpMap);
				for (int x = 0; x < warpMap.length; ++x){
					for (int y = 0; y < warpMap[0].length; ++y){
							if (warpMap[x][y] != 0){
								Point pt = warpPoints.get(warpMap[x][y]);
								System.out.println(x + "," + y + " " + 
										pt.x + "," + pt.y);
							}
					}
				}
			}
		}

		repaint();
	}
	@SuppressWarnings("static-access")
	public static void addWarpData(String name, Point pt){
		mk.warpNames.add(name);
		mk.warpPoints.add(pt);
		mk.getDrawPanel().refreshData();
		mk.getSelectPanel().refreshData();
	}
	public SelectPanel getSelectPanel(){
		return selectPnl;
	}
	public static void setNewMapDimensions(int x, int y){
		mapWidth = x; mapHeight = y;
		mk.baseMap = new double[mapWidth][mapHeight];
		mk.overMap = new double[mapWidth][mapHeight];
		mk.collMap = new double[mapWidth][mapHeight];
		mk.warpMap = new int[mapWidth][mapHeight];
		mk.warpNames = new ArrayList<String>();
		mk.warpNames.add("Eraser");
		mk.warpPoints = new ArrayList<Point>();
		mk.warpPoints.add(new Point(0,0));
		mk.updateDrawPanel();
		mk.getSelectPanel().refreshData();
		mk.repaint();
	}
	/**
	 * Synchronizes data between DrawPanel and MapMaker.
	 */
	private void updateDrawPanel(){
		MapMaker.drawPnl.setBaseData(mk.baseMap);
		MapMaker.drawPnl.setOverData(mk.overMap);
		MapMaker.drawPnl.setCollData(mk.collMap);
		MapMaker.drawPnl.setWarpData(mk.warpMap);
		MapMaker.drawPnl.setWarpNames(mk.warpNames);
		MapMaker.drawPnl.setWarpPoints(mk.warpPoints);
	}
	public static HashMap<Double, Image> getBaseInv(){
		return baseInv;
	}
	public static HashMap<Double, Image> getOverInv(){
		return overInv;
	}
	public static HashMap<Double, Image> getCollInv(){
		return collInv;
	}
	public static double[][] getBaseData(){
		return mk.baseMap;
	}
	public static double[][] getOverData(){
		return mk.overMap;
	}
	public static double[][] getCollData(){
		return mk.collMap;
	}
	public static int[][] getWarpData(){
		return mk.warpMap;
	}
	public static ArrayList<String> getWarpNames(){
		return mk.warpNames;
	}
	public static ArrayList<Point> getWarpPoints(){
		return mk.warpPoints;
	}
	public static double getSelectedTile(){
		return selectPnl.getSTile();
	}
	/**
	 * Checks to see if the tile to be painted is already of the selected tile
	 * type. For example, if painting road tiles, will return true if the tile
	 * is already road.
	 * @param x : x location of the subject tile.
	 * @param y : y location of the subject tile.
	 * @return : true if same, false otherwise.
	 */
	public static boolean sameBaseTile(int x, int y){
		if (mk.baseMap[x][y] == selectPnl.getSTile()){
			return true;
		} else{
			return false;
		}
	}
	/**
	 * Changes the tile to the selected tile type.
	 * @param x : x location of the subject tile.
	 * @param y : y location of the subject tile.
	 */
	public static void changeBaseTile(int x, int y){
		mk.baseMap[x][y] = selectPnl.getSTile();
	}
	/**
	 * Checks to see if the tile to be painted is already of the selected tile
	 * type. For example, if painting UL_fence tiles, will return true if the
	 * tile is already a UL_fence.
	 * @param x : x location of the subjecy tile.
	 * @param y : y location of the subject tile.
	 * @return : true if same, false otherwise.
	 */
	public static boolean sameOverTile(int x, int y){
		if (mk.overMap[x][y] == selectPnl.getSTile()){
			return true;
		} else{
			return false;
		}
	}
	/**
	 * Changes the tile to the selected tile type.
	 * @param x : x location of the subject tile.
	 * @param y : y location of the subject tile.
	 */
	public static void changeOverTile(int x, int y){
		mk.overMap[x][y] = selectPnl.getSTile();
	}
	/**
	 * Check to see if the tile to be painted is already of the selected tile
	 * type. For example, if painting no/x tiles, will return true if the tile
	 * is already no/x.
	 * @param x : x location of the tile in question.
	 * @param y : y location of the tile in question.
	 * @return : true if same, false otherwise.
	 */
	public static boolean sameCollTile(int x, int y){
		if (mk.collMap[x][y] == selectPnl.getSTile()){
			return true;
		} else{
			return false;
		}
	}
	/**
	 * Changes the tile to the selected tile type.
	 * @param x : x location of the subject tile.
	 * @param y : y location of the subject tile.
	 */
	public static void changeCollTile(int x, int y){
		mk.collMap[x][y] = selectPnl.getSTile();
	}
	/**
	 * Check to see if the tile to be painted is already of the selected tile
	 * type. For example, when painting a warp, will return true if tile
	 * already has warp of that type.
	 * @param x : x location of the tile in question.
	 * @param y : y location of the tile in question.
	 * @return : true if same, false otherwise.
	 */
	public static boolean sameWarpTile(int x, int y){
		if (mk.warpMap[x][y] == (int)selectPnl.getSTile()){
			return true;
		} else{
			return false;
		}
	}
	/**
	 * Changes the tile to the selected tile type.
	 * @param x : x location of the subject tile.
	 * @param y : y location of the subject tile.
	 */
	public static void changeWarpTile(int x, int y){
		mk.warpMap[x][y] = (int)selectPnl.getSTile();
	}
	public static DrawPanel getDrawPanel(){
		return drawPnl;
	}
}
