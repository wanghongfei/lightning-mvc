package cn.fh.lightning.bean;

import java.util.List;

/**
 * The basic and simplest interface for bean container.
 */
public interface BeanContainer {
	public String CONTAINER_ATTIBUTE = "BEAN_CONTAINER_ATTRIBUTE";

	/**
	 * Retrieve bean from container.
	 * @param name
	 * @return
	 */
	public Object getBean(String name);
	
	/**
	 * Add a new bean to container.
	 * @param bean
	 */
	public void registerBean(Bean bean);
	
	public void registerBeans(List<Bean> beanList);
}
