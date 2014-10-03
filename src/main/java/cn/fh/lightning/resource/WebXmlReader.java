package cn.fh.lightning.resource;

import java.util.List;

import org.w3c.dom.Element;

import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.mvc.RequestType;
import cn.fh.lightning.mvc.UrlRequestMap;

public class WebXmlReader extends XmlReader {
	public WebXmlReader(String... xmlPaths) {
		this.resources = new Resource[xmlPaths.length];
		
		int ix = 0;
		for (String path : xmlPaths) {
			this.resources[ix++] = new ClassPathXmlResource(path);
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
		
		Bean mapBean = new UrlRequestMap(url, controller, RequestType.valueOf(reqType));
		beanList.add(mapBean);
	}
}
