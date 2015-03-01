package cn.fh.lightning.bean;

import java.util.Map;

/**
 * The implementation of this interface is a component holder that wraps the actual
 * bean object and encapsulate more information about that bean.
 */
public interface Bean {
    /**
     * Get a {@code Map} that describes the dependency information of this bean.
     * @return A {@code Map} that describes the dependencies this bean needs.
     * <p> The key of the {@code Map} is the field's name of this bean, and the value of
     * the {@code Map} is the desired component name of this field.
     */
	public Map<String, String> getDependencies();

    /**
     * Get the unique identification name of this bean.
     * @return the unique identification name of this bean.
     */
	public String getBeanName();

    /**
     * Get the canonical name of this bean's {@code Class}.
     * @return the canonical name of this bean's {@code Class}.
     */
	public String getBeanClass();

    /**
     * Returns the object that this bean wraps in.
     * @return The object that this bean wraps in.
     */
	public Object getActualBean();
}
