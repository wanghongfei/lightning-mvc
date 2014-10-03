package cn.fh.lightning.mvc.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasicServlet extends HttpServlet {
	public static Logger logger = LoggerFactory.getLogger(BasicServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("正在初始化[" + getClass().getName() + "]");

		// 由子类实现该方法
		initServlet(config.getServletContext());

		logger.info("[" + getClass().getName() + "]初始化完成");
	}
	
	protected abstract void processRequest(HttpServletRequest req, HttpServletResponse resp);
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		processRequest(req, resp);
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		processRequest(req, resp);
	}


	protected abstract void initServlet(ServletContext ctx);
	

}
