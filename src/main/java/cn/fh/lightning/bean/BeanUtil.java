package cn.fh.lightning.bean;

import cn.fh.lightning.exception.BeanNotFoundException;

import java.util.Map;

/**
 * Utility class for bean creation.
 */
public class BeanUtil {
	/**
	 * Create a singleton bean.
	 *
	 * @param id The unique identification of this bean.
	 * @param className The fully qualified name of this bean class.
     * @return Return the created new bean if everything is OK, otherwise return null.
	 *
     * @throws BeanNotFoundException Cannot initialize this bean with its fully qualified name.
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
     * Create a singleton bean with Class object.
     */
    public static Bean createSingletonBean(String id, Class clazz) {
        Bean bean = null;

        try {
            bean = new SingletonBean(id, createInstance(clazz));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return bean;
    }
    
    public static Bean createSingletonBean(String id, Class clazz, Map<String, String> propMap) {
        Bean bean = null;

        try {
            bean = new SingletonBean(id, createInstance(clazz), propMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return bean;
    }

	/**
     * Initialize a singleton bean using a Map of dependencies.
     *
	 * @param id The unique identification of this bean.
	 * @param className The fully qualified name of the bean class. e.g., java.lang.String
	 * @param propMap A Map that contains key-value pairs that describe its dependencies.
     *
	 * @return Return the created new bean if everything is OK, otherwise return null.
	 *
     * @throws BeanNotFoundException Cannot initialize this bean with its fully qualified name.
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

    public static Object createInstance(Class clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
	
}
