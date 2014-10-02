package cn.fh.lighting.bean;

import java.util.Map;

public interface Bean {
	public Map<String, String> getDependencies();
	public String getBeanName();
	public String getBeanClass();
	
	public Object getActuallBean();
}
