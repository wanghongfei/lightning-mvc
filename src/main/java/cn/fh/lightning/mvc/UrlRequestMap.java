package cn.fh.lightning.mvc;

import java.util.Map;

/**
 * 一个URL请求对应一个Controller
 * @author whf
 *
 */
public class UrlRequestMap implements RequestMap {
	private String url;
	private String controllerName;
	private RequestType requestType;
	
	public UrlRequestMap() {
		
	}
	
	public UrlRequestMap(String url, String controllerName, RequestType type) {
		this.url = url;
		this.controllerName = controllerName;
		this.requestType = type;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getControllerName() {
		return this.controllerName;
	}

	@Override
	public RequestType getRequestType() {
		return this.requestType;
	}

	// *** 以下为空实现 *** //
	@Override
	public Map<String, String> getDependencies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBeanName() {
		return null;
	}

	@Override
	public String getBeanClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getActuallBean() {
		// TODO Auto-generated method stub
		return null;
	}
}
