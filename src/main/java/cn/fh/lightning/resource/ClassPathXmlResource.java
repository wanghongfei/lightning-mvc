package cn.fh.lightning.resource;

import java.io.IOException;
import java.io.InputStream;

public class ClassPathXmlResource implements Resource {
	private String path;
	
	public ClassPathXmlResource(String path) {
		this.path = path;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return getClass().getClassLoader().getResourceAsStream(path);
	}

}
