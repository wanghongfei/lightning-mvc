package cn.fh.lightning.bean.container;

import cn.fh.lightning.bean.Bean;

import java.util.List;

/**
 * The basic and simplest interface for bean container.
 */
public interface BeanContainer {
	public String CONTAINER_ATTIBUTE = "BEAN_CONTAINER_ATTRIBUTE";

	/**
	 * Retrieve the object the bean wraps from container.
	 * @param name The name of the desired bean.
	 * @return Object that the bean wraps.
	 */
	public Object getBean(String name);
	
	/**
	 * Add a new bean to container.
	 * @param bean Bean to be registered to this container.
	 */
	public void registerBean(Bean bean);

    /**
     * Add multiple beans to container.
     * @param beanList A {@code List} of @{code Bean}
     *
     * @see #registerBean
     */
	public void registerBeans(List<Bean> beanList);
}
