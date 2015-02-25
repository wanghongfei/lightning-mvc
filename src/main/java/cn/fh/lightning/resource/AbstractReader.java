package cn.fh.lightning.resource;

import cn.fh.lightning.bean.Bean;

import java.util.List;

public abstract class AbstractReader implements Reader {
	protected Resource[] resources;

	
	@Override
	public List<Bean> loadBeans() {
        // delegate to doLoadBean() method
		return doLoadBeans();
	}

    /**
     * This method will be invoked in {@link #loadBeans()} method.
     * Derived class should override this method.
     */
	protected abstract List<Bean> doLoadBeans();

}
