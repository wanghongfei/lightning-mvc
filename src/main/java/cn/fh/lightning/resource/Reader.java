package cn.fh.lightning.resource;

import cn.fh.lightning.bean.Bean;

import java.util.List;

/**
 * Implementations of this interface are responsible to convert configurations to
 * a {@link java.util.List} of {@link cn.fh.lightning.bean.Bean}s.
 */
public interface Reader {
    /**
     * Load bean definitions from {@link cn.fh.lightning.resource.Resource} and return
     * a list of bean as a result.
     * @return A list of Bean.
     */
	public List<Bean> loadBeans();
}
