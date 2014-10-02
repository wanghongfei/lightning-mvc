package cn.fh.lightning.bean;

public class BeanUtil {
	/**
	 * 使用默认构造方法创建Bean实例
	 * 
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Bean createInstance(Class<Bean> clazz) throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}
	
	/**
	 * 根据类名得到类的Class对象
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getBeanClass(String className) throws ClassNotFoundException {
		return Class.forName(className);
	}
}
