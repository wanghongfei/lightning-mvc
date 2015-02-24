package cn.fh.lightning.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * This class partly implements the Bean interface.
 */
public abstract class AbstractBean implements Bean {
	/**
	 * A map containing the properties for this bean.
	 */
	protected Map<String, String> dependencyMap = new HashMap<String, String>();

	/**
	 * The actual bean component object.
	 */
	protected Object actuallBean;

	protected String beanName;
	
	/**
	 * Construct a bean component with name.
	 *
	 * @param beanName
	 * @param obj
	 */
	public AbstractBean(String beanName, Object obj) {
		this.beanName = beanName;
		this.actuallBean = obj;
	}

	/**
	 * Construct bean component with name and properties.
	 *
	 * @param beanName
	 * @param obj
	 * @param propMap A map containing the properties.
	 */
	public AbstractBean(String beanName, Object obj, Map<String, String> propMap) {
		this(beanName, obj);
		
		String name = null;
		String beanClass = null;

		for (Map.Entry<String, String> entry : propMap.entrySet()) {
			name = entry.getKey();
			beanClass = entry.getValue();
			
			
			this.dependencyMap.put(name, beanClass);
		}
	}
	
	@Override
	public Object getActuallBean() {
		return this.actuallBean;
	}

	@Override
	public Map<String, String> getDependencies() {
		return this.dependencyMap;
	}

	@Override
	public String getBeanName() {
		return this.beanName;
	}

	@Override
	public String getBeanClass() {
		return this.actuallBean.getClass().getName();
	}

	/**
	 * Use bean name to determine 2 beans are equal or not.
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (false == obj instanceof AbstractBean) {
			return false;
		}
		
		AbstractBean other = (AbstractBean)obj;
		if (null == this.beanName || null == other.beanName) {
			return false;
		}

		return this.beanName.equals(other.beanName);
	}

	@Override
	public String toString() {
		return "Bean Name:" + this.beanName + ", Class:" + getClass().getName();
	}
	
	
}
