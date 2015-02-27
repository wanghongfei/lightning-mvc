package cn.fh.lightning;

import java.net.URL;

public class StringUtil {
    private StringUtil() {

    }

    /**
     * "/WEB-INF/classes/cn/fh/StringUtil.class" -> "StringUtil"
     */
    public static String trimClassNameFromPath(String path) {
        int splashPos = path.lastIndexOf('/');
        int dotPos = path.indexOf('.');

        return path.substring(splashPos + 1, dotPos);
    }

    /**
     * "/WEB-INF/classes/cn/fh/StringUtil" -> "cn.fh.StringUtil"
     */
    public static String splitClassNameFromPath(String path) {
        int splashPos = path.lastIndexOf("/WEB-INF/classes/") + "/WEB-INF/classes/".length();
        int pointPos = path.lastIndexOf('.');

        String className = path.substring(splashPos, pointPos).replace('/', '.');

        return className;
    }

    /**
     * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
     * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
     */
    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    /**
     * "cn.fh.lightning" -> "cn/fh/lightning"
     * @param name
     * @return
     */
    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * "Apple.class" -> "Apple"
     */
    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

	/**
	 * /application/home -> /home
	 * @param uri
	 * @return
	 */
	public static String trimURI(String uri) {
		String trimmed = uri.substring(1);
		int splashIndex = trimmed.indexOf('/');
		
		return trimmed.substring(splashIndex);
	}
}
