package cn.fh.lightning.bean.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cn.fh.lightning.bean.BasicInjectableBeanContainer;
import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.bean.SingletonBean;
import cn.fh.lightning.resource.Reader;
import cn.fh.lightning.resource.XmlReader;

public class BasicInjectableContainerTest {
	@Test
	public void injectByXmlTest() throws Exception {
		BasicInjectableBeanContainer con = new BasicInjectableBeanContainer();
		
		Reader reader = new XmlReader("config.xml");
		con.registerBeans(reader.loadBeans());
		
		Orange orange = (Orange) con.getBeanWithDependencies("orange");
		assertNotNull("没有得到orange", orange);
		assertNotNull("orange的依赖apple对象没有被注入", orange.getApple());
	}
	
	@Test
	public void multiplyInjectTest() throws Exception {
		BasicInjectableBeanContainer con = new BasicInjectableBeanContainer();
		
		Map<String, String> propMap = new HashMap<String, String>();
		propMap.put("orange1", "orange");
		propMap.put("orange2", "orange");
		

		Bean bean1 = new SingletonBean("apple", Apple.class.newInstance(), propMap);
		Bean bean2 = new SingletonBean("orange", Orange.class.newInstance());
		con.registerBean(bean1);
		con.registerBean(bean2);
		

		Apple apple = (Apple) con.getBeanWithDependencies("apple");
		assertNotNull("没有得到bean", apple);
		assertEquals("bean错误", "apple", apple.toString());
		assertNotNull("orange1没有注入", apple.getOrange1());
		assertNotNull("orange2没有注入", apple.getOrange2());
	}
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
		propMap.put("orange1", "orange");
		
		

		Bean bean1 = new SingletonBean("apple", Apple.class.newInstance(), propMap);
		Bean bean2 = new SingletonBean("orange", Orange.class.newInstance());
		con.registerBean(bean1);
		con.registerBean(bean2);
		

		Apple apple = (Apple) con.getBeanWithDependencies("apple");
		assertNotNull("没有得到bean", apple);
		assertEquals("bean错误", "apple", apple.toString());
		assertNotNull("orange1没有注入", apple.getOrange1());
	}

	@Test
	public void nameTest() {
		String appleName = Apple.class.getCanonicalName();

		assertEquals("类全名错误", "cn.fh.lightning.bean.test.Apple", appleName);
	}
}
