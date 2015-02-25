package cn.fh.lightning.mvc;

/**
 * This interface is exposed to the framework users in order to let them
 * add data to model.
 */
public interface Model {
	public void addAttribute(String name, Object attr);
}
