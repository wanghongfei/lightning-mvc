package cn.fh.lightning.bean.scan;

import java.io.IOException;
import java.util.List;

/**
 * Implementations of this interface should be capable of
 * listing all classes in a specific package.
 *
 * Created by whf on 15-2-26.
 */
public interface PackageScanner {
    List<String> getFullyQualifiedClassNameList() throws IOException;
}
