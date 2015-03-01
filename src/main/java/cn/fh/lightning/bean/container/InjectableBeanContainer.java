package cn.fh.lightning.bean.container;

/**
 * Interface for Bean Containers that support dependency injection.
 */
public interface InjectableBeanContainer extends BeanContainer {
	/**
	 * Retrieve bean from container with all its dependencies injected.
	 * @param beanName The name of the desired bean.
	 * @return The component with all its dependencies injected.
	 */
	public Object getBeanWithDependencies(String beanName);
}
