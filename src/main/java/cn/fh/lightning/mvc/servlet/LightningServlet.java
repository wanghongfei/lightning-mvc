package cn.fh.lightning.mvc.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.fh.lightning.bean.BasicInjectableBeanContainer;
import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.bean.InjectableBeanContainer;
import cn.fh.lightning.exception.BeanNotFoundException;
import cn.fh.lightning.mvc.BasicModel;
import cn.fh.lightning.mvc.Controller;
import cn.fh.lightning.mvc.InternalModel;
import cn.fh.lightning.mvc.RequestMap;
import cn.fh.lightning.mvc.RequestType;
import cn.fh.lightning.mvc.exception.InvalidControllerException;
import cn.fh.lightning.mvc.exception.ViewNotFoundException;
import cn.fh.lightning.resource.Reader;
import cn.fh.lightning.resource.WebXmlReader;
import cn.fh.lightning.resource.XmlReader;

public class LightningServlet extends BasicServlet implements ServletContextListener {
	public static Logger logger = LoggerFactory.getLogger(LightningServlet.class);

	public static String CONFIGURE_FILE_ATTRIBUTE = "CONFIGURE_FILE_LOCATION";
	public static String BEAN_CONTAINER_ATTRIBUTE = "BEAN_CONTAINER_ATTIBUTE";
	
	/**
	 * 默认的bean文件路径
	 */
	public static String DEFAULT_CONFIGURE_FILE_LOCATION = "/WEB-INF/lighting-config.xml";
	public static String DEFAULT_WEB_CONFIGURE_FILE_LOCATION = "/WEB-INF/lighting-url-map.xml";
	
	
	private RequestMap[] reqMaps;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 在此创建IoC容器
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 得到bean文件路径
		String configFile = getServletConfig().getServletContext().getInitParameter(CONFIGURE_FILE_ATTRIBUTE);
		if (null == configFile) {
			configFile = DEFAULT_CONFIGURE_FILE_LOCATION;
		}
		
		// 解析bean文件
		logger.info("正在解析bean配置文件");
		Reader reader = new XmlReader(configFile);
		// 创建IoC容器
		logger.info("正在启动IoC容器");
		InjectableBeanContainer ioc = new BasicInjectableBeanContainer();
		// 注册bean
		ioc.registerBeans(reader.loadBeans());
		// 将容器放到Servet上下文中
		getServletConfig().getServletContext().setAttribute(BEAN_CONTAINER_ATTRIBUTE, ioc);

		logger.info("容器启动完毕");
	}




	/**
	 * GET, POST请求都会交由此方法处理
	 */
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) {
		// 得到请求类型
		RequestType reqType = RequestType.valueOf(req.getMethod());
		
		if (logger.isDebugEnabled()) {
			logger.debug("收到请求[" + req.getRequestURI() + "]");
		}
		
		// 得到控制器
		RequestMap rMap = findController(req.getRequestURI(), reqType);
		if (null == rMap) {
			logger.info("没有与[" + req.getRequestURI() + "]对应的控制器!");
			return;
		}
		
		// 从容器中取出controller
		String controllerName = rMap.getControllerName();
		Bean bean =(Bean) getContainer().getBeanWithDependencies(controllerName);
		if (null == bean) {
			throw new BeanNotFoundException("没有找到[" + controllerName + "]");
		}
		Object cl = bean.getActuallBean();
		if (false == cl instanceof Controller) {
			throw new InvalidControllerException("Controller[" + controllerName + "]非法:没有实现Controller接口");
		}

		// 调用业务逻辑方法
		InternalModel model = new BasicModel();
		Controller controller = (Controller)cl;
		String viewName = controller.handle(req, model);

		
		
		// 将model里的数据放到request对象中
		if (false == model.getAttrMap().isEmpty()) {
			for (Map.Entry<String, Object> entry : model.getAttrMap().entrySet()) {
				req.setAttribute(entry.getKey(), entry.getValue());
			}
		}
		
		// 转发请求至指定jsp页面
		if (null == viewName) {
			throw new ViewNotFoundException("视图名不能为空!");
		}
		try {
			getServletContext().getRequestDispatcher("/WEB-INF/views/" + viewName).forward(req, resp);
		} catch (ServletException | IOException e) {
			throw new ViewNotFoundException("找不到[" + viewName + "]");
		}
	}
	
	/**
	 * 根据URL的请求类型找到合适的控制器
	 * 
	 * @param url
	 * @param reqType
	 * @return
	 */
	private RequestMap findController(String url, RequestType reqType) {
		for (RequestMap rMap : this.reqMaps) {
			if (rMap.getUrl().equals(url) && rMap.getRequestType() == reqType) {
				return rMap;
			}
		}
		
		return null;
	}

	/**
	 * 初始化Servlet.该方法会被父类的init()方法调用
	 */
	@Override
	protected void initServlet() {
		initRequestMap();
		logger.info("加载url映射完毕");
	}
	
	private void initRequestMap() {
		Reader reader = new WebXmlReader(DEFAULT_WEB_CONFIGURE_FILE_LOCATION);
		this.reqMaps = (RequestMap[]) reader.loadBeans().toArray();
		//getContainer().registerBeans(reader.loadBeans());
	}
	
	private InjectableBeanContainer getContainer() {
		return (InjectableBeanContainer) getServletConfig().getServletContext().getAttribute(BEAN_CONTAINER_ATTRIBUTE);
	}

}
