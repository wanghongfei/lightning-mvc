package cn.fh.lighting.bean.test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import org.junit.Test;

import cn.fh.lighting.bean.BasicInjectableBeanContainer;
import cn.fh.lighting.bean.Bean;
import cn.fh.lighting.bean.SingletonBean;

public class BasicInjectableContainerTest {
	@Test
	public void containerTest() throws Exception {
		BasicInjectableBeanContainer con = new BasicInjectableBeanContainer();

		// Map<String, String> propMap = new HashMap<String, String>();
		// propMap.put("apple", Apple.class.getCanonicalName());
		// propMap.put("orange", Orange.class.getCanonicalName());

		Bean bean = new SingletonBean("apple", Apple.class.newInstance());
		con.registerBean(bean);

		Apple apple = (Apple) con.getBean("apple");
		assertNotNull("没有得到bean", apple);
		assertEquals("bean错误", "apple", apple.toString());
	}

	@Test
	public void injectTest() throws Exception {
		BasicInjectableBeanContainer con = new BasicInjectableBeanContainer();

		Map<String, String> propMap = new HashMap<String, String>();
		propMap.put("orange", "orange");
		
		

		Bean bean1 = new SingletonBean("apple", Apple.class.newInstance(), propMap);
		Bean bean2 = new SingletonBean("orange", Orange.class.newInstance());
		con.registerBean(bean1);
		con.registerBean(bean2);
		

		Apple apple = (Apple) con.getBeanWithDependencies("apple");
		assertNotNull("没有得到bean", apple);
		assertEquals("bean错误", "apple", apple.toString());
		assertNotNull("orange没有注入", apple.getOrange());
	}

	@Test
	public void nameTest() {
		String appleName = Apple.class.getCanonicalName();

		assertEquals("类全名错误", "cn.fh.lighting.bean.test.Apple", appleName);
	}
}
