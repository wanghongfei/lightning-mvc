package cn.fh.lightning.bean.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.fh.lightning.bean.Bean;
import cn.fh.lightning.bean.SingletonBean;
import cn.fh.lightning.resource.Reader;
import cn.fh.lightning.resource.XmlReader;

public class XmlReaderTest {

	@Test
	public void xmlTest() {
		Reader reader = new XmlReader("config.xml");
		
		List<Bean> beanList = reader.loadBeans();
		Assert.assertFalse(beanList.isEmpty());
		
		Assert.assertArrayEquals("bean错误", new Bean[]{new SingletonBean("apple", "cn.fh.lightning.bean.test.Apple"), new SingletonBean("orange", "cn.fh.lightning.bean.test.Orange")}, beanList.toArray());

	}
	
	@Test
	public void refTest() {
		Reader reader = new XmlReader("config.xml");
		
		List<Bean> beanList = reader.loadBeans();
		
		Bean orangeBean = beanList.get(1);
		String depApple = orangeBean.getDependencies().get("apple");
		Assert.assertEquals("依赖错误", "apple", depApple);
	}
}
