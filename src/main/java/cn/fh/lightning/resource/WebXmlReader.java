package cn.fh.lightning.resource;

import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.mvc.RequestType;
import cn.fh.lightning.mvc.UrlRequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * This class is used to convert XML configuration file to
 * a {@link java.util.List} of {@link cn.fh.lightning.bean.Bean}.
 */
public class WebXmlReader extends XmlReader {
	public static Logger logger = LoggerFactory.getLogger(WebXmlReader.class);

	public WebXmlReader(ServletContext ctx, String... xmlPaths) {
		this.resources = new Resource[xmlPaths.length];
		
		int ix = 0;
		for (String path : xmlPaths) {
			this.resources[ix++] = new ClassPathXmlResource(path, ctx);
		}
		
	}
	
	public WebXmlReader(Resource... resources) {
		super(resources);
	}

	/**
     * Analyze <code><url-map></code> tag.
	 */
	@Override
	protected void parseBeanTag(Element beanTag, Resource r, List<Bean> beanList) {
		String url = beanTag.getAttribute("url");
		String controller = beanTag.getAttribute("controller");
		String reqType = beanTag.getAttribute("request-type");
		
		if (logger.isDebugEnabled()) {
			logger.debug("<" + beanTag.getTagName() + ">");
			logger.debug("读入URL map:[" + url + "],[" + controller + "],[" + reqType + "]");
		}
		
		Bean mapBean = new UrlRequestMapping(url, controller, RequestType.valueOf(reqType));
		beanList.add(mapBean);
	}
}
