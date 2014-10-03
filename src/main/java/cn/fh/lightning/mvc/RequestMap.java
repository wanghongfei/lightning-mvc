package cn.fh.lightning.mvc;

import cn.fh.lightning.bean.Bean;

public interface RequestMap extends Bean {
	public String getUrl();
	public String getControllerName();
	public RequestType getRequestType();
}
