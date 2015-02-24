package cn.fh.lightning.mvc;

import cn.fh.lightning.bean.Bean;

public interface RequestMapping extends Bean {
	public String getUrl();
	public String getControllerName();
	public RequestType getRequestType();
}
