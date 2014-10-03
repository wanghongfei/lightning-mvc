package cn.fh.lightning.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import cn.fh.lightning.mvc.exception.ResourceNotFoundException;

public class ClassPathXmlResource implements Resource {
	private String path;
	private ServletContext ctx;
	
	public ClassPathXmlResource(String path, ServletContext ctx) {
		this.path = path;
		this.ctx = ctx;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		//InputStream in = getClass().getClassLoader().getResourceAsStream(path);
		InputStream in = ctx.getResourceAsStream(path);
		if (null == in) {
			throw new ResourceNotFoundException("资源[" + path + "]不存在");
		}
		
		return in;
	}

}
