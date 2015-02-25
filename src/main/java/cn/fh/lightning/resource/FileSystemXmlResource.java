package cn.fh.lightning.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class represents a {@link cn.fh.lightning.resource.Resource} in the form
 * of a XML file in file system.
 *
 * @author whf
 */
public class FileSystemXmlResource implements Resource {
	private String path;

	/**
     * Construct a new object with the path of the XML file.
	 * @param path The path in file system of the XML file.
	 */
	public FileSystemXmlResource(String path) {
		this.path = path;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		File xmlFile = new File(path);
		
		return new FileInputStream(xmlFile);
	}
	
	@Override
	public String toString() {
		return "xml[" + this.path + "]";
	}

}
