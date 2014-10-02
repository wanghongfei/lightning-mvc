package cn.fh.lighting.bean;

public interface InjectableBeanContainer extends BeanContainer {
	public Object getBeanWithDependencies(String beanName);
}
