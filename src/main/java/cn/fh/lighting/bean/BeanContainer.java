package cn.fh.lighting.bean;

public interface BeanContainer {
	public String CONTAINER_ATTIBUTE = "BEAN_CONTAINER_ATTRIBUTE";
	
	public Object getBean(String name);
	
	/**
	 * 向容器注册一个bean对象
	 * @param bean
	 */
	public void registerBean(Bean bean);
}
