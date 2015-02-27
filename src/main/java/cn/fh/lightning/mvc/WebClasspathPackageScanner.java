package cn.fh.lightning.mvc;

import cn.fh.lightning.StringUtil;
import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.bean.scan.PackageScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by whf on 15-2-27.
 */
public class WebClasspathPackageScanner implements PackageScanner, Bean {
    private Logger logger = LoggerFactory.getLogger(WebClasspathPackageScanner.class);

    private ServletContext ctx;
    private String basePackage;

    public WebClasspathPackageScanner(String basePackage, ServletContext ctx) {
        this.basePackage = basePackage;
        this.ctx = ctx;
    }

    // *** methods required by PackageScanner interface *** //

    @Override
    public List<String> getFullyQualifiedClassNameList() throws IOException {
        return doScan(basePackage, new ArrayList<>());
    }

    private List<String> doScan(String basePackage, List<String> nameList) {
        Set<String> nameSet = ctx.getResourcePaths("/WEB-INF/classes/" + StringUtil.dotToSplash(basePackage));

        if (null == nameSet || nameSet.isEmpty()) {
            return null;
        }

        for (String name : nameSet) {
            if (isClassFile(name)) {
                // this is a class file
                String canonicalName = StringUtil.splitClassNameFromPath(name);
                if (logger.isDebugEnabled()) {
                    logger.debug("发现组件{}", canonicalName);
                }

                nameList.add(canonicalName);
            } else {
                // this is a directory
                // do recursive invocation
                doScan(basePackage + "." + StringUtil.trimClassNameFromPath(name), nameList);
            }
        }

        return nameList;
    }

    // *** methods required by Bean interface *** //

    @Override
    public Map<String, String> getDependencies() {
        return null;
    }

    @Override
    public String getBeanName() {
        return "webClasspathPackageScanner";
    }

    @Override
    public String getBeanClass() {
        return WebClasspathPackageScanner.class.getCanonicalName();
    }

    @Override
    public Object getActualBean() {
        return this;
    }

    private boolean isClassFile(String name) {
        return name.endsWith(".class");
    }
}
