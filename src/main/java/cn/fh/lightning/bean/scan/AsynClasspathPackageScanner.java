package cn.fh.lightning.bean.scan;

import cn.fh.lightning.mvc.WebClasspathPackageScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * This class is the asynchronized version of {@link cn.fh.lightning.mvc.WebClasspathPackageScanner},
 * which means the scanning task will be done in a separate thread.
 * <p> This class should be used in web environment only.
 */
public class AsynClasspathPackageScanner extends WebClasspathPackageScanner implements Callable<List<String>> {
    private Logger logger = LoggerFactory.getLogger(AsynClasspathPackageScanner.class);

    private Future<List<String>> scanFuture;

    /**
     * Once the object is constructed, it starts a new thread to do
     * the scanning task.
     */
    public AsynClasspathPackageScanner(String basePackage, ServletContext ctx) {
        super(basePackage, ctx);

        scanFuture = scanInNewThread();
    }

    /**
     * Please never call this method manually.
     */
    @Override
    public List<String> call() {
        List<String> nameList = null;
        nameList = doScan(basePackage, new ArrayList<>());

        return nameList;
    }

    /**
     * This method will return immediately. If the task has been finished,
     * a list of canonical names will be returned. Otherwise null is returned.
     *
     * @throws IOException If something wrong with the procedure of scanning happens.
     */
    @Override
    public List<String> getCanonicalNameList() throws IOException {
        List<String> result = null;

        // inquire of whether task has been finished
        if (scanFuture.isDone()) {
            try {
                result = scanFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new IOException("扫描组件期间发生错误");
            } catch (ExecutionException e) {
                e.printStackTrace();
                throw new IOException("扫描组件期间发生错误");
            }
        }

        return result;
    }

    private Future<List<String>> scanInNewThread() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        return executor.submit(this);
    }
}
