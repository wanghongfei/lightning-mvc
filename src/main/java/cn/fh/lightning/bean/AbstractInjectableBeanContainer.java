package cn.fh.lightning.bean;

import cn.fh.lightning.exception.*;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Partial implementation of the {@link InjectableBeanContainer} interface.
 */
public abstract class AbstractInjectableBeanContainer implements InjectableBeanContainer {
	public static Logger logger;

	/**
	 * This map contains all bean components.
	 */
	protected Map<String, Bean> beanMap = new HashMap<String, Bean>();
	

	@Override
	public final Object getBean(String name) {
		Bean bean = this.beanMap.get(name);

		return bean == null ? null : bean.getActualBean();
	}

	@Override
	public final void registerBean(Bean bean) {
		// register a singleton bean
		if (bean instanceof SingletonBean) {
			SingletonBean sBean = (SingletonBean)bean;

			// determine whether this name already exists in container
			if (false == this.beanMap.containsKey(sBean)) {
				this.beanMap.put(sBean.getBeanName(), sBean);
			} else {
				throw new BeanAlreadyExistException("bean名[" + bean.getBeanName() + "]已经在容器中存在. [bean " + bean.getBeanName() + " already exists]");
			}
		} else {
			throw new InvalidBeanTypeException("不支持该bean类型:" + bean.getBeanClass() + ", [unsupported bean type:" + bean.getBeanClass() + "]");
		}
	}
	
	@Override
	public final void registerBeans(List<Bean> beanList) {
		for (Bean bean : beanList) {
			registerBean(bean);
		}
	}

	@Override
	public final Object getBeanWithDependencies(String beanName) {
		Bean bean = this.beanMap.get(beanName);
		
		if (logger.isDebugEnabled()) {
			logger.debug("查找[" + beanName + "]");
		}

		// no bean found
		if (null == bean) {
			return null;
		}

		// bean is found
		// prepare for DI
		Object actObject = bean.getActualBean();
		Map<String, String> depMap = bean.getDependencies();

		// this bean has no dependencies
		if (true == depMap.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("[" + bean.getBeanName() + "]没有依赖");
			}

			return actObject;
		} else {
			// perform DI
			if (logger.isDebugEnabled()) {
				logger.debug("[" + bean.getBeanName() + "]有依赖");
			}

			// get all declared fields of this component
			Field[] fields = actObject.getClass().getDeclaredFields();
			
			
			for (Map.Entry<String, String> entry : depMap.entrySet()) {
				if (logger.isDebugEnabled()) {
					logger.debug("[" + bean.getBeanName() + "]查找依赖:" + entry.getKey());
				}

				// recursive invocation
				// retrieve the dependency from container
				Object depObject = getBeanWithDependencies(entry.getValue());

				// throw Runtime exception if dependency was not found
				if (null == depObject) {
					throw new BeanNotFoundException("容器中没有找到[" + entry.getValue() + "]");
				}

				// inject dependency in reflection way.
				injectField(actObject, fields, entry.getKey(), depObject);
			}
		}
		
		return actObject;
	}
	
	/**
     * Inject value to the specified field in target object.
	 *
	 * @param objToInject The target object that contains the field.
	 * @param allFields An array of {@link java.lang.reflect.Field} objects that declared in the target object.
	 * @param fieldName The name of the field to inject to.
	 * @param fieldValue The value of the field to be injected.
	 * 
	 * @return Return true indicates successful injection, otherwise return false.
	 */
	protected boolean injectField(Object objToInject, Field[] allFields, String fieldName, Object fieldValue) {
		for (int ix = 0 ; ix < allFields.length ; ++ix) {
			if (allFields[ix].getName().equals(fieldName)) {

				// this field could be private,
				// so I have to change its accessibility to public in order to set its value
				allFields[ix].setAccessible(true);

				try {
					allFields[ix].set(objToInject, fieldValue);
				} catch (IllegalArgumentException e) {
					//logger.error("注入成员变量[" + allFields[ix].getName() + "]失败");
					e.printStackTrace();
                    throw new InjectionFailedException("注入成员变量[" + allFields[ix].getName() + "]失败. Injection to field [" + allFields[ix].getName() + "] failed");
				} catch (IllegalAccessException e) {
					//logger.error("注入成员变量[" + allFields[ix].getName() + "]失败");
					e.printStackTrace();
                    throw new InjectionFailedException("注入成员变量[" + allFields[ix].getName() + "]失败. Injection to field [" + allFields[ix].getName() + "] failed");
				}

				return true;
			}
		}
		
		return false;
	}
	
}
