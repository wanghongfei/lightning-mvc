package cn.fh.lightning.bean;

import cn.fh.lightning.exception.BeanNotFoundException;

import java.util.Map;

public class BeanUtil {
	/**
	 * Create a singleton bean.
	 *
	 * @param id
	 * @param className
	 * @return
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static Bean createSingletonBean(String id, String className) {
		Bean bean = null;

		try {
			bean = new SingletonBean(id, createInstance(className));
		} catch (Exception ex) {
			throw new BeanNotFoundException("没有找到类[" + className + "]");
		}
		
		return bean;
	}
	
	/**
	 * 用指定的依赖实例化一个单例bean
	 * @param id
	 * @param className
	 * @param propMap
	 * @return
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static Bean createSingletonBean(String id, String className, Map<String, String> propMap) {
		Bean bean = null;
		try {
			bean = new SingletonBean(id, createInstance(className), propMap);
		} catch (Exception ex) {
			throw new BeanNotFoundException("没有找到类[" + className + "]");
		}
		
		return bean;
	}
	
	public static Object createInstance(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return Class.forName(className).newInstance();
	}
	
}
