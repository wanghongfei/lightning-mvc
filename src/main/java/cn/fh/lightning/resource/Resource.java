package cn.fh.lightning.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * This interface represents a resource, which can be a file, URL, or something that
 * can be recognized as a kind of resource.
 */
public interface Resource {
	public InputStream getInputStream() throws IOException;
}
