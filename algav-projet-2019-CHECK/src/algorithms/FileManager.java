package algorithms;


import java.io.File;


public class FileManager {

	private int indexFile = 0;
	private File dir;
	private String currFileName;

	public FileManager (String dir) {
		this.dir = new File(dir);
	}

	public String getNextFile() {
		indexFile = (indexFile + 1);
		if (indexFile >= dir.listFiles().length)
			return null;
		while (!(dir.listFiles()[indexFile].isFile())) {
			indexFile = (indexFile + 1);
			if (indexFile >= dir.listFiles().length)
				return null;
		}
		currFileName = dir.listFiles()[indexFile].getName();
		return dir +"/"+ dir.listFiles()[indexFile].getName();
	}

	public String getCurrentFileName() {
		return currFileName;
	}

}