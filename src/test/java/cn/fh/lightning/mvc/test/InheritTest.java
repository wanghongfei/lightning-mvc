package cn.fh.lightning.mvc.test;

import org.junit.Assert;
import org.junit.Test;

public class InheritTest extends Parent {
	@Test
	public void test() {
		InheritTest myClass = new InheritTest();
		Assert.assertEquals("", "cn.fh.lightning.mvc.test.InheritTest", myClass.name());
	}
}

abstract class Parent {
	protected String name() {
		return getClass().getName();
	}
}
