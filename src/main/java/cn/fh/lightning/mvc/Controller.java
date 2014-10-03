package cn.fh.lightning.mvc;

import javax.servlet.http.HttpServletRequest;

public interface Controller {
	/**
	 * 
	 * @param request
	 * @return 返回一个jsp文件名
	 */
	public String handle(HttpServletRequest request, Model model);
}
