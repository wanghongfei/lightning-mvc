package cn.fh.lightning.bean;

import java.util.Map;

/**
 * Bean living in singleton scope.
 * @author whf
 *
 */
public class SingletonBean extends AbstractBean implements Bean {

    /**
     * Construct a bean instance scoped to singleton scope.
     *
     * @param beanName The unique identification name of this bean.
     * @param obj The object that this bean wraps.
     */
	public SingletonBean(String beanName, Object obj) {
		super(beanName, obj);
	}

    /**
     *
     * Construct a bean instance scoped to singleton scope, with a {@code map}
     * describes information about its dependencies.
     *
     * @param beanName The unique identification name of this bean.
     * @param obj The object that this bean wraps.
     * @param propMap Contains information about its dependencies.
     */
	public SingletonBean(String beanName, Object obj, Map<String, String> propMap) {
		super(beanName, obj, propMap);
	}
}
