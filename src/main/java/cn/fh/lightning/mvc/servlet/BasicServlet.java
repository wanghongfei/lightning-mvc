package cn.fh.lightning.mvc.servlet;

import cn.fh.lightning.mvc.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BasicServlet extends HttpServlet {
	public static Logger logger = LoggerFactory.getLogger(BasicServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("正在初始化[" + getClass().getName() + "]");

        // derived class will do initial job
		initServlet(config);

		logger.info("[" + getClass().getName() + "]初始化完成");
	}

    /**
     * Derived class should implement this method to actually process request.
     */
	protected abstract void processRequest(HttpServletRequest req, HttpServletResponse resp, Constants.RequestMethod reqMethod);

    /**
     * Derived class should implement this method to do initialization job.
     */
    protected abstract void initServlet(ServletConfig cfg);

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		processRequest(req, resp, Constants.RequestMethod.GET);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		processRequest(req, resp, Constants.RequestMethod.POST);
	}
}
