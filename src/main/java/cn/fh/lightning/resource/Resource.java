package cn.fh.lightning.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * This interface represents a resource, which can be a file, URL, or something that
 * can be recognized as a kind of resource.
 */
public interface Resource {
    /**
     * Open a stream for this resource.
     * @return The {@code InputStream} instance for this resource.
     * @throws IOException If an I/O error occurs.
     */
	public InputStream getInputStream() throws IOException;
}
