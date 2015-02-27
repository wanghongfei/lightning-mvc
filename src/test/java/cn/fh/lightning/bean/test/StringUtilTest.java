package cn.fh.lightning.bean.test;

import cn.fh.lightning.StringUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by whf on 15-2-27.
 */
public class StringUtilTest {
    @Test
    public void testTrimClassNameFromPath() {
        String path = "/WEB-INF/classes/cn/fh/StringUtil.class";
        String res = StringUtil.trimClassNameFromPath(path);

        Assert.assertEquals("StringUtil", res);
    }

    /*@Test
    public void testGetRootPath() throws MalformedURLException {
        URL u1 = new URL("file:/home/whf/cn/fh");
        URL u2 = new URL("URL:jar:file:/home/whf/foo.jar!/cn/fh");

        String res1 = StringUtil.getRootPath(u1);
        Assert.assertEquals("/home/whf/cn/fh", res1);

        String res2 = StringUtil.getRootPath(u2);
        Assert.assertEquals("/home/whf/foo.jar", res2);
    }*/
}
