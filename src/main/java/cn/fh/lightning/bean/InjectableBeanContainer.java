package cn.fh.lightning.bean;

/**
 * Interface for Bean Containers that support dependency injection.
 */
public interface InjectableBeanContainer extends BeanContainer {
	/**
	 * Retrieve bean from container with all its dependencies injected.
	 * @param beanName
	 * @return
	 */
	public Object getBeanWithDependencies(String beanName);
}
