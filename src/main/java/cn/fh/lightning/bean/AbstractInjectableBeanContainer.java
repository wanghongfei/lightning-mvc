package cn.fh.lightning.bean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import cn.fh.lightning.exception.BeanAlreadyExistException;
import cn.fh.lightning.exception.BeanNotFoundException;
import cn.fh.lightning.exception.InvalidBeanTypeException;

public abstract class AbstractInjectableBeanContainer implements InjectableBeanContainer {
	public static Logger logger;

	/**
	 * 持有bean对象
	 */
	protected Map<String, Bean> beanMap = new HashMap<String, Bean>();
	

	/**
	 * 只获取bean, 不进行DI
	 */
	@Override
	public final Object getBean(String name) {
		Bean bean = this.beanMap.get(name);

		return bean == null ? null : bean.getActuallBean();
	}

	@Override
	public final void registerBean(Bean bean) {
		// 注册一个单例bean
		if (bean instanceof SingletonBean) {
			SingletonBean sBean = (SingletonBean)bean;
			
			// 判断容器中是否已经存在一个此bean了
			if (false == this.beanMap.containsKey(sBean)) {
				this.beanMap.put(sBean.getBeanName(), sBean);
			} else {
				throw new BeanAlreadyExistException("bean名[" + bean.getBeanName() + "]已经在容器中存在");
			}
		} else {
			throw new InvalidBeanTypeException("不支持该bean类型:" + bean.getBeanClass());
		}
	}

	/**
	 * 获取bean并执行DI操作
	 */
	@Override
	public final Object getBeanWithDependencies(String beanName) {
		Bean bean = this.beanMap.get(beanName);
		
		if (logger.isDebugEnabled()) {
			logger.debug("查找[" + beanName + "]");
		}
		// 容器中没有此bean
		if (null == bean) {
			return null;
		}
		
		// 找到此bean,查检依赖
		Object actObject = bean.getActuallBean();
		Map<String, String> depMap = bean.getDependencies();

		// 该bean没有依赖
		if (true == depMap.isEmpty()) {
			if (logger.isDebugEnabled()) {
				logger.debug("[" + bean.getBeanName() + "]没有依赖");
			}

			return actObject;
		} else { // 依赖注入
			if (logger.isDebugEnabled()) {
				logger.debug("[" + bean.getBeanName() + "]有依赖");
			}
			
			// 得到该类声明的所有成员变量
			Field[] fields = actObject.getClass().getDeclaredFields();
			
			
			for (Map.Entry<String, String> entry : depMap.entrySet()) {
				// 递归调用
				// 从容器中查找actObject所依赖的类
				if (logger.isDebugEnabled()) {
					logger.debug("[" + bean.getBeanName() + "]查找依赖:" + entry.getKey());
				}

				Object depObject = getBeanWithDependencies(entry.getKey());

				if (null == depObject) {
					throw new BeanNotFoundException("容器中没有找到[" + entry.getKey() + "]");
				}
				
				injectField(actObject, fields, entry.getKey(), depObject);
			}
		}
		
		return actObject;
	}
	
	/**
	 * 向指定成员变量注入指定的对象
	 * 
	 * @param objToInject 包含该成员变量的对象
	 * @param allFields 对象的所有成员变量
	 * @param fieldName 要注入的成员变量名
	 * @param fieldValue 要注入的值
	 * 
	 * @return
	 */
	protected boolean injectField(Object objToInject, Field[] allFields, String fieldName, Object fieldValue) {
		for (int ix = 0 ; ix < allFields.length ; ++ix) {
			if (allFields[ix].getName().equals(fieldName)) {

				try {
					allFields[ix].set(objToInject, fieldValue);
				} catch (IllegalArgumentException e) {
					logger.error("注入成员变量[" + allFields[ix].getName() + "]失败");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					logger.error("注入成员变量[" + allFields[ix].getName() + "]失败");
					e.printStackTrace();
				}

				return true;
			}
		}
		
		return false;
	}
	
}
