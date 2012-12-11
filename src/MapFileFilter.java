import java.io.File;

import javax.swing.filechooser.FileFilter;

//tommy

public class MapFileFilter extends FileFilter{
	public boolean accept(File aFile) {
		if (aFile.isDirectory()){
			return true;
		}
		if (FileManager.getExtension(aFile).compareTo(".map") == 0){
			return true;
		}
		return false;
	}
	public String getDescription() {
		return null;
	}
}
