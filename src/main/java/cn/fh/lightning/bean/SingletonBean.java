package cn.fh.lightning.bean;

import java.util.Map;

/**
 * 单例bean
 * @author whf
 *
 */
public class SingletonBean extends AbstractBean implements Bean {

	public SingletonBean(String beanName, Object obj) {
		super(beanName, obj);
	}

	public SingletonBean(String beanName, Object obj, Map<String, String> propMap) {
		super(beanName, obj, propMap);
	}
}
