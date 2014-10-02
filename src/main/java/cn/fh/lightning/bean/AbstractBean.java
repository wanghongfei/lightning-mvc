package cn.fh.lightning.bean;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBean implements Bean {
	protected Map<String, String> dependencyMap = new HashMap<String, String>();

	protected Object actuallBean;
	/**
	 * bean在容器中的名字
	 */
	protected String beanName;
	
	/**
	 * 通过指定bean名和实际的bean对象来构造bean
	 * 
	 * @param beanName
	 * @param obj 实际的对象
	 */
	public AbstractBean(String beanName, Object obj) {
		this.beanName = beanName;
		this.actuallBean = obj;
	}
	
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

		return this.beanName.equals(other);
	}

	@Override
	public String toString() {
		return "Bean Name:" + this.actuallBean + ", Class:" + getClass().getName();
	}
	
	
}
