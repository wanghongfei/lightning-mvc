package cn.fh.lightning.resource;

import java.util.List;

import cn.fh.lightning.bean.Bean;

public abstract class AbstractReader implements Reader {
	protected Resource[] resources;

	
	@Override
	public List<Bean> loadBeans() {
		// 具体载入过程由子类实现
		return doLoadBeans();
	}
	
	protected abstract List<Bean> doLoadBeans();

}
