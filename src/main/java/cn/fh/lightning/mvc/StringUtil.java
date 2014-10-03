package cn.fh.lightning.mvc;

public class StringUtil {
	/**
	 * 将 /application/home 变成/home
	 * @param uri
	 * @return
	 */
	public static String trimURI(String uri) {
		String trimmed = uri.substring(1);
		int splashIndex = trimmed.indexOf('/');
		
		return trimmed.substring(splashIndex);
	}
}
