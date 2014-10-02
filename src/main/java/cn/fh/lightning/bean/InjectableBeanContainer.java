package cn.fh.lightning.bean;

public interface InjectableBeanContainer extends BeanContainer {
	public Object getBeanWithDependencies(String beanName);
}
