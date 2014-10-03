package cn.fh.lightning.resource;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.mvc.RequestType;
import cn.fh.lightning.mvc.UrlRequestMap;

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
	 * 解析<url-map>标签
	 */
	@Override
	protected void parseBeanTag(Element beanTag, Resource r, List<Bean> beanList) {
		String url = beanTag.getAttribute("url");
		String controller = beanTag.getAttribute("controller");
		String reqType = beanTag.getAttribute("request-type");
		
		System.out.println(beanTag.getTagName());
		System.out.println("读入URL map:[" + url + "],[" + controller + "],[" + reqType + "]");
		
		Bean mapBean = new UrlRequestMap(url, controller, RequestType.valueOf(reqType));
		beanList.add(mapBean);
	}
}
