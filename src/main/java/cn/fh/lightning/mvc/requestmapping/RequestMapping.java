package cn.fh.lightning.mvc.requestmapping;

import cn.fh.lightning.bean.Bean;

/**
 * Implementation of this interface contains information about which controller should handle
 * which request(URL).
 *
 * @author whf
 */
public interface RequestMapping extends Bean {
	public String getUrl();
	public String getControllerName();
	public RequestType getRequestType();
}
