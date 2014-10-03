package cn.fh.lightning.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 一个XML配置文件资源
 * @author whf
 *
 */
public class FileSystemXmlResource implements Resource {
	private String path;

	/**
	 * 使用xml文件路径构造XmlResource对象
	 * @param path
	 */
	public FileSystemXmlResource(String path) {
		this.path = path;
	}

	/**
	 * 得到该xml文件的输入流
	 */
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
