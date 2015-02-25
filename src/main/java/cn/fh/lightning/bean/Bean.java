package cn.fh.lightning.bean;

import java.util.Map;

/**
 * The implementation of this interface is a component holder that wraps the actual
 * bean object and encapsulate more information about that bean.
 */
public interface Bean {
	public Map<String, String> getDependencies();
	public String getBeanName();
	public String getBeanClass();
	
	public Object getActualBean();
}
