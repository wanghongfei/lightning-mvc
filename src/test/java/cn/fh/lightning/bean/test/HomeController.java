package cn.fh.lightning.bean.test;

import cn.fh.lightning.mvc.Controller;
import cn.fh.lightning.mvc.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by whf on 15-2-26.
 */
public class HomeController implements Controller {
    @Override
    public String handle(HttpServletRequest request, Model model) {
        return "home.jsp";
    }
}
