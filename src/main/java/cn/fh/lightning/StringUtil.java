package cn.fh.lightning;

import java.net.URL;

public class StringUtil {
    private StringUtil() {

    }

    /**
     * From "/WEB-INF/classes/cn/fh/StringUtils.class" to "StringUtil"
     */
    public static String trimClassNameFromPath(String path) {
        int splashPos = path.lastIndexOf('/');
        int dotPos = path.indexOf('.');

        return path.substring(splashPos + 1, dotPos);
    }

    /**
     * From "file:/home/whf/cn/fh" to "/home/whf/cn/fh".
     * <p> From "url:jar:file:/home/whf/foo.jar!/cn/fh" to "/home/whf/foo.jar".
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
     * From "cn.fh.lightning" to "cn/fh/lightning"
     * @param name The package name to be transformed.
     */
    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * From "Apple.class" to "Apple"
     */
    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

	/**
	 * From "/application/home" to "/home".
	 */
	public static String trimURI(String uri) {
		String trimmed = uri.substring(1);
		int splashIndex = trimmed.indexOf('/');
		
		return trimmed.substring(splashIndex);
	}
}
