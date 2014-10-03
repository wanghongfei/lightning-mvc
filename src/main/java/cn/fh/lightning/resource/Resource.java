package cn.fh.lightning.resource;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {
	public InputStream getInputStream() throws IOException;
}
