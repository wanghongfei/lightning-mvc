package cn.fh.lightning.mvc;

import javax.servlet.http.HttpServletRequest;

/**
 * Classes implementing this interface indicates that this class
 * is a controller and is designed to handle request.
 */
public interface Controller {
	/**
     * This method will be invoked by framework to handle request.
	 * 
	 * @param request
	 * @return A jsp file name. e.g., home.jsp
	 */
	public String handle(HttpServletRequest request, Model model);
}
