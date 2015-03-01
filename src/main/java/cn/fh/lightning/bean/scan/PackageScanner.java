package cn.fh.lightning.bean.scan;

import java.io.IOException;
import java.util.List;

/**
 * Implementations of this interface should be capable of
 * listing all classes in a specific package.
 */
public interface PackageScanner {
    /**
     * Returns a {@code List} of {@code String} representing the canonical name
     * of one class. These names are used to create their instances by {@link java.lang.Class#forName} method.
     * @return
     * @throws IOException
     */
    List<String> getFullyQualifiedClassNameList() throws IOException;
}
