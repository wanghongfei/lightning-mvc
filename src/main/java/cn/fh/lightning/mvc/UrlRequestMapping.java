package cn.fh.lightning.mvc;

import java.util.Map;

/**
 * This class is used to group a controller and an URL together, indicating that this
 * controller is supposed to handle this URL.
 *
 * @author whf
 */
public class UrlRequestMapping implements RequestMapping {
	private String url;
	private String controllerName;
	private RequestType requestType;
	
	public UrlRequestMapping() {
		
	}
	
	public UrlRequestMapping(String url, String controllerName, RequestType type) {
		this.url = url;
		this.controllerName = controllerName;
		this.requestType = type;
	}

    // *** implementations for RequestMapping interface *** //
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



	// *** implementations for Bean interface *** //
	@Override
	public Map<String, String> getDependencies() {
		return null;
	}

	@Override
	public String getBeanName() {
        return "urlRequestMapping";
	}

	@Override
	public String getBeanClass() {
        return getClass().getCanonicalName();
	}

	@Override
	public Object getActualBean() {
        return this;
	}
}
