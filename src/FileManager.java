import java.awt.Point;
import java.io.*;
import java.util.ArrayList;

/**
 * List of static methods to read/write .map data.
 * @author Tommy
 * @version v1.2
 */
public class FileManager {
	/**
	 * Prints to console the data recieved. For debugging use only.
	 * @param mapData : The two-dimensional array to print.
	 */
	public static void printData1(double[][] mapData){
		String contents = "";
		for (int y = 0; y < mapData[0].length; ++y){
			for (int x = 0; x < mapData.length; ++x){
				contents += mapData[x][y];
				if (x < mapData.length - 1){
					contents += " ";
				} else{
					contents += "\n";
				}
			}
		}
		System.out.println(contents);
	}
	public static void printData2(int[][] mapData){
		String contents = "";
		for (int y = 0; y < mapData[0].length; ++y){
			for (int x = 0; x < mapData.length; ++x){
				contents += mapData[x][y];
				if (x < mapData.length - 1){
					contents += " ";
				} else{
					contents += "\n";
				}
			}
		}
		System.out.println(contents);
	}
	/**
	 * Returns the extension of the file. Includes period. Example: File with name "aFile.map" will return ".map"
	 * @param aFile : The subject file.
	 * @return : A String containing the extension of the file.
	 */
	public static String getExtension(File aFile){
		String name = aFile.getName();
		for (int x = name.length() - 1; x > 0; --x){
			if (name.charAt(x) == '.'){
				return name.substring(x);
			}
		}
		return "";
	}
	/**
	 * Converts double[][] to a String. Elements are separated by ' ' and '\n', indicating row and column.
	 * @param mapData : The subject array to convert.
	 * @return : A String representation of the array.
	 */
	public static String dataToString1(double[][] mapData){
		String contents = "";
		for (int y = 0; y < mapData[0].length; ++y){
			for (int x = 0; x < mapData.length; ++x){
				contents += mapData[x][y];
				if (x < mapData.length - 1){
					contents += " ";
				} else{
					contents += "\n";
				}
			}
		}

		return contents;
	}
	/**
	 * Converts int[][] to a String. Elements are separated by ' ' and '\n', indicating row and column.
	 * @param warpData : The subject array to convert.
	 * @return : A String representation of the array.
	 */
	public static String dataToString2(int[][] warpData){
		String contents = "";
		for (int y = 0; y < warpData[0].length; ++y){
			for (int x = 0; x < warpData.length; ++x){
				contents += warpData[x][y];
				if (x < warpData.length - 1){
					contents += " ";
				} else{
					contents += "\n";
				}
			}
		}
		return contents;
	}
	public static String dataToString3(ArrayList<String> warpNames){
		String contents = "";
		for (int index = 0; index < warpNames.size(); ++index){
			contents += warpNames.get(index);
			if (index < warpNames.size() - 1){
				contents += " ";
			} else{
				contents += "\n";
			}
		}
		return contents;
	}
	public static String dataToString4(ArrayList<Point> warpPoints){
		String contents = "";
		if (warpPoints.size() == 0){
			return "null\n";
		}
		for (int index = 0; index < warpPoints.size(); ++index){
			Point pt = warpPoints.get(index);
			contents += (int)pt.getX() + "," + (int)pt.getY();
			if (index < warpPoints.size() - 1){
				contents += " ";
			} else{
				contents += "\n";
			}
		}
		return contents;
	}
	/**
	 * Converts String to Point. Used to find dimensions of map.
	 * @param contents : String data from file.
	 * @return : Point whose x and y values represent map dimensions.
	 */
	public static Point strToPoint(String contents){
		int delimiterIndex = -1;
		for (int index = 0; index < contents.length(); ++index){
			if (contents.charAt(index) == ','){
				delimiterIndex = index;
				break;
			}
		}
		int x = Integer.parseInt(contents.substring(0, delimiterIndex));
		contents = contents.substring(delimiterIndex + 1);
		for (int index = 0; index < contents.length(); ++index){
			if (contents.charAt(index) == '\n'){
				delimiterIndex = index;
				break;
			}
		}
		int y = Integer.parseInt(contents.substring(0, delimiterIndex));
		return new Point(x,y);
	}
	/**
	 * Converts map data from String to <code>double[][][]</code>, where the array's first brackets represent layers.
	 * @param contents : String data from file.
	 * @param mapSet : Array of map data (first three layers).
	 * @return : Leftover String data.
	 */
	public static String readMapData1(String contents) throws Exception{
		//TODO These methods are poorly named! Change them!! :(
		try{
			int count = 0;
			while(contents.charAt(count) != '\n'){
				++count;
			}
			Point pt = strToPoint(contents.substring(0, count));
			contents = contents.substring(count + 1);
			int x = (int) pt.getX(), y = (int) pt.getY();

			double[][][] mapSet = new double[3][x][y];
			//Uses a process/chop method on contents
			int delimit =  breakPoint(contents, y, '\n');
			mapSet[0] = processDoubleArray(x, y, contents.substring(0, delimit));
			contents = contents.substring(delimit + 1);
			delimit = breakPoint(contents, y, '\n');
			mapSet[1] = processDoubleArray(x, y, contents);
			contents = contents.substring(delimit + 1);
			delimit = breakPoint(contents, y, '\n');
			mapSet[2] = processDoubleArray(x, y, contents);
			contents = contents.substring(delimit + 1);
			MapMaker.setMapData1(mapSet);
			return contents;
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("Cannot read basic layers data.");
		}
	}
	/**
	 * Finds point where delimiter occurs. KEY NOTE: When looking for delimiter '\n',
	 * keep in mind that the index returned will be the start of '\n', and that '\n' acts
	 * as if it takes 2 element spaces in some cases. 
	 * @param contents : String data from file.
	 * @param y : Number of occurences of delimiter to look for.
	 * @return : Index of delimiter. Returns the yth instance of delimiter.
	 */
	public static int breakPoint(String contents, int y, char delimiter){
		int numReturns = 0;
		for (int index = 0; index < contents.length(); ++index){
			if (contents.charAt(index) == delimiter){
				++numReturns;
				if (numReturns == y){
					return index;
				}
			}
		}
		return -1;
	}
	/**
	 * Converts map data from String to int[][], which represents warp data.
	 * @param contents : String data from file.
	 * @param warpMap : Array to store warp data.
	 * @return : Leftover String data.
	 */
	public static String readMapData2(String contents, int[][] warpMap) throws Exception{
		try{
			int x = warpMap.length, y = warpMap[0].length;
			int delimit = breakPoint(contents, y, '\n');
			warpMap = processIntArray(x, y, contents.substring(0, delimit -1));
			contents = contents.substring(delimit + 1);
			MapMaker.setMapData2(warpMap);
			return contents;
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("Cannot read warp layer data.");
		}
	}
	/**
	 * Converts map data from String to ArrayList<String>, which represents warp map names.
	 * @param contents : String data from file.
	 * @param warpNames : ArrayList to store names.
	 * @return : Leftover String data.
	 */
	public static String readMapData3(String contents, ArrayList<String> warpNames) throws Exception{
		try{
			int delimit = breakPoint(contents, 1, '\n');
			String line = contents.substring(0, delimit - 1);
			contents = contents.substring(delimit + 1);
			if (line.equals("null")){
				return contents;
			}
			int start = 0;
			for (int index = 0; index < line.length(); ++index){
				if (line.charAt(index) == ' '){
					warpNames.add(line.substring(start, index));
					start = index + 1;
				}
			}
			warpNames.add(line.substring(start));
			MapMaker.setMapData3(warpNames);
			return contents;
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("Cannot read warp name data.");
		}
	}
	/**
	 * Converts map data from String to ArrayList<Point>, which represents warp points. Note: "Warp points" means the places to warp TO, not FROM. These points specify destinations to OTHER maps, not the current one.
	 * @param contents : String data from file.
	 * @param warpPoints : ArrayList to store points.
	 * @return : Leftover String data, or empty string "" if no data follows.
	 */
	public static String readMapData4(String contents, ArrayList<Point> warpPoints) throws Exception{
		try{
			int delimiter = breakPoint(contents, 1, '\n');
			String line = contents.substring(0, delimiter - 1);
			if (contents.length() > delimiter + 1){
				contents = contents.substring(delimiter + 1);
			} else {
				contents = "";
			}
			if (line.equals("null")){
				return contents;
			}
			for (int index = 0; index < line.length(); ++index){
				int delimit = breakPoint(line, 1, ' ');
				if (delimit == -1){
					break;
				}
				warpPoints.add(strToPoint(line.substring(0, delimit)));
				line = line.substring(delimit + 1);
			}
			warpPoints.add(strToPoint(line));
			MapMaker.setMapData4(warpPoints);
			return contents;
		} catch(Exception e){
			e.printStackTrace();
			throw new Exception("Cannot read warp point data.");
		}
	}
	/**
	 * Converts data from String to int[][].
	 * @param x : Width of map.
	 * @param y : Height of map.
	 * @param contents : String data from file.
	 * @param warpMap : Array to store data.
	 * @return : Leftover String data.
	 */
	private static int[][] processIntArray(int x, int y, String contents){
		int[][] mapData = new int[x][y];
		for (int j = 0; j < y; ++j){
			String line = "";
			int delimiter = breakPoint(contents, 1, '\n');
			if (delimiter == -1){
				line = contents;
			} else{
				line = contents.substring(0, delimiter - 1);
				contents = contents.substring(delimiter + 1);
			}
			for (int i = 0; i < x; ++i){
				delimiter = breakPoint(line, 1, ' ');
				if (delimiter == -1){
					mapData[i][j] = Integer.parseInt(line.substring(0, line.length()));
				} else{
					mapData[i][j] = Integer.parseInt(line.substring(0, delimiter));
					line = line.substring(delimiter + 1);
				}
			}
		}
		return mapData;
	}
	/**
	 * Converts the String data into a map array. Assumes the string has more data after the current array being read.
	 * @param x : The width of the subject map.
	 * @param y : The height of the subject map.
	 * @param contents : The String data of the map.
	 * @return : Leftover String data.
	 */
	private static double[][] processDoubleArray(int x, int y, String contents){
		double[][] mapData = new double[x][y];
		for (int j = 0; j < y; ++j){
			String line = "";
			int delimiter = breakPoint(contents, 1, '\n');
			if (delimiter == -1){
				line = contents;
			} else{
				line = contents.substring(0, delimiter - 1);
				contents = contents.substring(delimiter + 1);
			}
			for (int i = 0; i < x; ++i){
				delimiter = breakPoint(line, 1, ' ');
				if (delimiter == -1){
					mapData[i][j] = Double.parseDouble(line.substring(0, line.length()));
				} else{
					mapData[i][j] = Double.parseDouble(line.substring(0, delimiter));
					line = line.substring(delimiter + 1);
				}
			}
		}
		return mapData;
	}
	/**
	 * Writes a String to the indicated File.
	 * @param aFile : File to write to.
	 * @param aContents : String to write to file.
	 * @throws IOException
	 */
	public static void writeFile(File aFile, String aContents) throws IOException{
		aFile = new File(aFile.getAbsolutePath());
//		FileOutputStream output = new FileOutputStream(aFile);
		Writer output = new BufferedWriter(new FileWriter(aFile,false));
		try{
			output.write(aContents);
		} finally{
			output.close();
		}
	}
	/**
	 * Reads the indicated file, and returns a String representation.
	 * @param aFile : The subject File.
	 * @return A String representation of the file.
	 */
	public static String readFile(File aFile){
		StringBuilder contents = new StringBuilder();
		try{
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try{
				String line = null;
				while ((line = input.readLine()) != null){
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally{
				input.close();
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		return contents.toString();
	}
}
/*
 * Some notes about .map format:
 * -Begins with dimensions of map, followed by several arrays representing each layer.
 * -After arrays, warp map names, then next line warp map points.
 * -File reader/writer allows for extra data to follow. This accounts for adding extra
 *  features to .map files without affecting compatability with MapEditor.
 * 
 * Pseudo:
 * >WIDTH,HEIGHT
 * >BASE BASE BASE
 * >BASE BASE BASE
 * >OVER OVER OVER
 * >OVER OVER OVER
 * >COLL COLL COLL
 * >COLL COLL COLL
 * >WARP WARP WARP
 * >WARP WARP WARP
 * >WARP_NAME.map WARP_NAME.map WARP_NAME.map
 * >WARP_POINT WARP_POINT WARP_POINT
 * >EXTRA
 * 
 * Example:
 * 2,2
 * 1.0 1.0
 * 1.1 1.1
 * 0.0 0.0
 * 0.0 0.0
 * 1.0 1.0
 * 1.0 1.0
 * 0 2
 * 0 1
 * some.map another.map
 * 2,1 3,1
 * blahblahblahblah
 * asdfghjkl
 */