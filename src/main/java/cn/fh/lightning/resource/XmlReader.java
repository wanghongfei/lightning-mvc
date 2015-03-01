package cn.fh.lightning.resource;

import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.bean.BeanUtil;
import cn.fh.lightning.exception.InvalidBeanXmlConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

public class XmlReader extends AbstractReader {
	public static Logger logger = LoggerFactory.getLogger(XmlReader.class);
	
	
	public XmlReader() {
		
	}
	
	
	/**
     * Construct an instance with the path of the configurations.
     *
     * @param ctx If this class is used in web environment, you should pass
     *            an instance of {@link javax.servlet.ServletContext}. Otherwise
     *            you should set it to {@code null}.
	 * @param xmlPaths The path of configuration files. If {@code ctx} is not set
     *                 to null, path should be a classpath. Otherwise it should be
     *                 a file system path.
	 */
	public XmlReader(ServletContext ctx, String... xmlPaths) {
		this.resources = new Resource[xmlPaths.length];

		int ix = 0;
		for (String path : xmlPaths) {
			// 如果路径不以 "/" 开头，则加上 "/"
			/*if (false == path.startsWith("/")) {
				path = "/" + path.substring(0);
			}*/
			
			if (null == ctx) {
				this.resources[ix++] = new FileSystemXmlResource(path);
			} else {
				this.resources[ix++] = new ClassPathXmlResource(path, ctx);
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("xml资源:" + path);
			}
		}
	}
	
	public XmlReader(Resource... resources) {
		this.resources = Arrays.copyOfRange(resources, 0, resources.length);
	}

	@Override
	protected List<Bean> doLoadBeans() {
		// 用List存放通过解析XML文件而生成的Bean
		List<Bean> beanList = new ArrayList<Bean>();
		
		// 遍历每一个Resource, 依次解析
		for (Resource r : this.resources) {
			try {
				Document doc = getDocumentObject(r);
				
				// 根标签
				Element root = doc.getDocumentElement();
				
				if (false == root.getTagName().equals("lightning-config")) {
					throw new InvalidBeanXmlConfigurationException(r + ":根标签应为<lightning-config>");
				}
				
				// 得到根结点的子节点
				NodeList childList = root.getChildNodes();
				int len = childList.getLength();
				
				// 每个循环都是一个bean定义标签
				for (int ix = 0 ; ix < len ; ++ix) {
					Node item = childList.item(ix);
					if (item instanceof Element) {
						Element beanTag = (Element)item;
						
						// 解析<bean>标签
						parseBeanTag(beanTag, r, beanList);
						

					}/* else {
						throw new InvalidBeanXmlConfigurationException(r + ":标签非法");
					}*/
				}

				
			} catch (SAXException e) {
				throw new InvalidBeanXmlConfigurationException(r + ":语法错误");
			} catch (IOException e) {
				throw new RuntimeException("IO错误:读取" + r + "失败");
			} catch (ParserConfigurationException e) {
				throw new RuntimeException("读取" + r + "失败");
			}
		}
		return beanList;
	}
	
	/**
	 * Parse a single {@code <bean>} tag.
	 */
	protected void parseBeanTag(Element beanTag, Resource r, List<Bean> beanList) {
		// 解析bean的id和类名
		if (false == beanTag.hasAttribute("id") || false == beanTag.hasAttribute("class")) {
			throw new InvalidBeanXmlConfigurationException(r + ":缺少id或class属性");
		}

		String id = beanTag.getAttribute("id");
		String className = beanTag.getAttribute("class");
		if (true == id.isEmpty() || true == className.isEmpty()) {
			throw new InvalidBeanXmlConfigurationException(r + ":id或class属性不能为空");
		}
		
		// 解析bean的依赖
		// 将所有依赖都放到depMap中
		Map<String, String> depMap = new HashMap<String, String>();
		if (true == beanTag.hasChildNodes()) {
			NodeList depList = beanTag.getChildNodes();
			int LEN = depList.getLength();
			
			// 每一个循环都是一个表达依赖的标签
			for (int i = 0 ; i < LEN ; ++i ) {
				Node depItem = depList.item(i);
				if (depItem instanceof Element) {
					Element depTag = (Element)depItem;
					
					// 解析<prop>标签
					parseBeanDepTag(depTag, r, depMap);

				}
			} // for-loop ends
			
			// 创建有依赖的Bean
			Bean bean = BeanUtil.createSingletonBean(id, className, depMap);
			beanList.add(bean);
			
		} else { // 没有依赖，直接创建并注册bean
			Bean bean = BeanUtil.createSingletonBean(id, className);
			beanList.add(bean);
		}
	}
	
	/**
     * Parse a single {@code <prop>} tag.
	 */
	private void parseBeanDepTag(Element depTag, Resource r, Map<String, String> depMap) {
		if (false == depTag.getTagName().equals("prop")) {
			throw new InvalidBeanXmlConfigurationException(r + ":<bean>标签内只能有<prop>标签");
		}
		
		String propName = depTag.getAttribute("name");
		String propClass = depTag.getAttribute("ref-class");
		
		depMap.put(propName, propClass);
	}
	
	private Document getDocumentObject(Resource r) throws SAXException,  IOException, ParserConfigurationException {
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(r.getInputStream());
	}
	

}
