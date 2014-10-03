package cn.fh.lightning.bean;

import java.util.List;

public interface BeanContainer {
	public String CONTAINER_ATTIBUTE = "BEAN_CONTAINER_ATTRIBUTE";
	
	public Object getBean(String name);
	
	/**
	 * 向容器注册一个bean对象
	 * @param bean
	 */
	public void registerBean(Bean bean);
	
	public void registerBeans(List<Bean> beanList);
}
