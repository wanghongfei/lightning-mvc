package cn.fh.lightning.security;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cn.fh.lightning.security.exception.InvalidXmlFileException;

public class PageProtectionFilter implements Filter, ServletContextListener {
	public static Logger logger = LoggerFactory.getLogger(PageProtectionFilter.class);
	
	public static Map<String, List<String>> requestRoleListMap;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Servlet上下文初始化时，解析secirity-page.xml文件
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("载入页面security配置文件");
		}

		try {
			Document doc = getXmlDocumentObject(event.getServletContext());
			Element root = doc.getDocumentElement();
			
			// 检查根标签名是否正确
			if (false == "page".equals(root.getTagName())) {
				throw new InvalidXmlFileException("标签<" + root.getTagName() + ">不存在");
			}
			
			PageProtectionFilter.requestRoleListMap = parseConstrain(root);


		} catch (SAXException e) {
			e.printStackTrace();
			throw new InvalidXmlFileException("xml语法错误");
		} catch (ParserConfigurationException e) {
			throw new InvalidXmlFileException("xml解析出错");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("页面security载入完毕");
		}
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String url = null;
		
		// 从URI中去掉上下文名
		String contextPath = req.getContextPath();
		if (false == "/".equals(contextPath)) {
			int ctxLen = req.getContextPath().length();
			url = req.getRequestURI().substring(ctxLen);
		} else {
			url = req.getRequestURI();
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("请求url:" + url);
		}
		
		// 检查权限
		if (false == checkRole(url, req.getSession())) {
			HttpServletResponse resp = (HttpServletResponse) response;
			OutputStream out = resp.getOutputStream();
			out.write("bad role".getBytes());
			out.close();
			
			return;
		}
		
		
		chain.doFilter(request, response);

	}
	
	/**
	 * 判断是否允许用户访问该页面
	 * @param requestURL
	 * @param session
	 * @return
	 */
	private boolean checkRole(String requestURL, HttpSession session) {
		List<String> roleList = PageProtectionFilter.requestRoleListMap.get(requestURL);
		
		// 该请求不需要role
		if (null == roleList) {
			return true;
		}
		
		// 得到Credential
		Credential credential = CredentialUtils.getCredential(session);
		// 未登陆
		if (null == credential) {
			return false;
		}
		// 检查role是否满足
		for (String role : roleList) {
			if (false == credential.hasRole(role)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 解析xml, 得到<url, 需要的roleList>形式的Map对象
	 * @param root
	 * @return
	 */
	private Map<String, List<String>> parseConstrain(Element root) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		if (root.hasChildNodes()) {
			NodeList nodes = root.getChildNodes();
			for (int ix = 0 ; ix < nodes.getLength() ; ++ix) {
				Node node = nodes.item(ix);
				if (node instanceof Element) {
					Element tag = (Element) node;
					
					// 检查标签名是否正确
					if (false == "request".equals(tag.getTagName())) {
						throw new InvalidXmlFileException("标签<" + tag.getTagName() + ">不存在");
					}
					
					// 检查属性是否存在
					String url = tag.getAttribute("url");
					String roles = tag.getAttribute("role");
					if (url.isEmpty()) {
						throw new InvalidXmlFileException("标签<" + tag.getTagName() + ">缺少'url'属性");
					}
					if (roles.isEmpty()) {
						throw new InvalidXmlFileException("标签<" + tag.getTagName() + ">缺少'role'属性");
					}
					
					// 构造roleList数据结构
					List<String> roleList = new ArrayList<String>();
					String[] roleArray = roles.split(" ");
					for (String r : roleArray) {
						roleList.add(r);
						
						if (logger.isDebugEnabled()) {
							logger.debug("找到role限制:<" + url + "> : " + r);
						}
					}
					
					// 构造map数据结构
					map.put(url, roleList);
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 得到XML文件的Document对象
	 * @param req
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	private Document getXmlDocumentObject(ServletContext ctx) throws SAXException, IOException, ParserConfigurationException {
		InputStream in = ctx.getResourceAsStream("/WEB-INF/security-page.xml");
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
