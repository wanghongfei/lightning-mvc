package cn.fh.lightning.mvc.servlet;

import cn.fh.lightning.bean.BasicInjectableBeanContainer;
import cn.fh.lightning.bean.InjectableBeanContainer;
import cn.fh.lightning.exception.BeanNotFoundException;
import cn.fh.lightning.mvc.*;
import cn.fh.lightning.mvc.exception.InvalidControllerException;
import cn.fh.lightning.mvc.exception.ViewNotFoundException;
import cn.fh.lightning.resource.Reader;
import cn.fh.lightning.resource.WebXmlReader;
import cn.fh.lightning.resource.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * This is the main servlet that starts the lightning-mvc framework.
 */
public class LightningServlet extends BasicServlet implements ServletContextListener {
	public static Logger logger = LoggerFactory.getLogger(LightningServlet.class);

	public static String CONFIGURE_FILE_ATTRIBUTE = "CONFIGURE_FILE_LOCATION";
	public static String BEAN_CONTAINER_ATTRIBUTE = "BEAN_CONTAINER_ATTRIBUTE";
	
	public static String DEFAULT_CONFIGURE_FILE_LOCATION = "/WEB-INF/lightning-config.xml";
	public static String DEFAULT_WEB_CONFIGURE_FILE_LOCATION = "/WEB-INF/lightning-url-map.xml";
	
	
	private RequestMapping[] reqMaps;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
     * Create IoC container.
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
        // get the path of the configuration file in web.xml
		String configFile = event.getServletContext().getInitParameter(CONFIGURE_FILE_ATTRIBUTE);
		if (null == configFile) {
            // use default path
			configFile = DEFAULT_CONFIGURE_FILE_LOCATION;
		}

        // parse configuration file
		logger.info("正在解析bean配置文件");
		Reader reader = new XmlReader(event.getServletContext(), configFile);
        // create IoC container instance
		logger.info("正在启动IoC容器");
		InjectableBeanContainer ioc = new BasicInjectableBeanContainer();
        // register beans parsed from configuration file
		ioc.registerBeans(reader.loadBeans());
        // put IoC container into servlet context
		event.getServletContext().setAttribute(BEAN_CONTAINER_ATTRIBUTE, ioc);

        // scan package to find out more component
		doPackageScan(event.getServletContext(), "cn.fh.sample");

		logger.info("容器启动完毕");
		
		
		// 载入web页面role配置
	}

	private String[] doPackageScan(ServletContext ctx, String packageName) {
		Set<String> classes = ctx.getResourcePaths("/WEB-INF/classes/" + packageName.replace('.', '/'));
		for (String name : classes) {
			System.out.println(splitClassName(name));
		}
		
		return null;
	}
	
	private String splitClassName(String path) {
		int splashPos = path.lastIndexOf("/WEB-INF/classes/") + "/WEB-INF/classes/".length();
		int pointPos = path.lastIndexOf('.');
		
		String className = path.substring(splashPos, pointPos).replace('/', '.');
		return className;
		
	}

    /**
     * This method actually handles the request.
     *
     * @param reqMethod The type of this request, could be GET or POST.
     */
	@Override
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp, Constants.RequestMethod reqMethod) {
		// 得到请求类型
		RequestType reqType = RequestType.valueOf(req.getMethod());
		
		String requestPath = StringUtil.trimURI(req.getRequestURI());
		
		if (logger.isDebugEnabled()) {
			logger.debug("收到请求[" + requestPath + "]");
		}

        // find out the request mapping handler that is supposed to handle this request
		RequestMapping rMap = findController(requestPath, reqType);
		if (null == rMap) {
			logger.info("没有与[" + requestPath + "]对应的控制器!");
			return;
		}

        // get the name of the controller that the request mapping handler is bound with
		String controllerName = rMap.getControllerName();
        // retrieve controller from container
		Object bean = getContainer(req).getBeanWithDependencies(controllerName);
		if (null == bean) {
			throw new BeanNotFoundException("没有找到[" + controllerName + "]");
		}
		//Object cl = bean.getActuallBean();
		if (false == bean instanceof Controller) {
			throw new InvalidControllerException("Controller[" + controllerName + "]非法:没有实现Controller接口");
		}

        // create model
		InternalModel model = new BasicModel();
		Controller controller = (Controller)bean;
        // invoke handle method
		String viewName = controller.handle(req, model);

		

        // transfer data from model to request attribute
		if (false == model.getAttrMap().isEmpty()) {
			for (Map.Entry<String, Object> entry : model.getAttrMap().entrySet()) {
				req.setAttribute(entry.getKey(), entry.getValue());
			}
		}

        // forward request
		if (null == viewName) {
			throw new ViewNotFoundException("视图名不能为空!");
		}
		try {
			req.getServletContext().getRequestDispatcher("/WEB-INF/views/" + viewName).forward(req, resp);
		} catch (ServletException | IOException e) {
			throw new ViewNotFoundException("找不到[" + viewName + "]");
		}
	}
	
	/**
     * Find the {@link cn.fh.lightning.mvc.RequestMapping} implementation that contains controller that
     * is supposed to handle this request
	 *
	 * @return The implementation of {@link cn.fh.lightning.mvc.RequestMapping} interface
	 */
	private RequestMapping findController(String url, RequestType reqType) {
		for (RequestMapping rMap : this.reqMaps) {
			if (rMap.getUrl().equals(url) && rMap.getRequestType() == reqType) {
				return rMap;
			}
		}
		
		return null;
	}

	/**
     * Do initialization jobs.
     * This method will be invoked when this servlet is created.
	 */
	@Override
	protected void initServlet(ServletContext ctx) {
        // load request mapping configuration from configuration file
		initRequestMapping(ctx);
		logger.info("加载url映射完毕");
	}
	
	private void initRequestMapping(ServletContext ctx) {
		Reader reader = new WebXmlReader(ctx, DEFAULT_WEB_CONFIGURE_FILE_LOCATION);
		this.reqMaps = reader.loadBeans().toArray(new UrlRequestMapping[1]);
		//getContainer().registerBeans(reader.loadBeans());
	}
	
	private InjectableBeanContainer getContainer(HttpServletRequest req) {
		return (InjectableBeanContainer)req.getServletContext().getAttribute(BEAN_CONTAINER_ATTRIBUTE);
	}

}
