package cn.fh.lightning.resource;

import cn.fh.lightning.bean.Bean;

import java.util.List;

/**
 * Implementations of this interface are responsible to convert configurations to
 * a {@link java.util.List} of {@link cn.fh.lightning.bean.Bean}s.
 */
public interface Reader {
	public List<Bean> loadBeans();
}
