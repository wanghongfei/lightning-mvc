package cn.fh.lightning.bean;

import org.slf4j.LoggerFactory;

/**
 * Default implementation for {@link AbstractInjectableBeanContainer}
 */
public class BasicInjectableBeanContainer extends
		AbstractInjectableBeanContainer {
	
	static {
		logger = LoggerFactory.getLogger(BasicInjectableBeanContainer.class);
	}


}