package cn.fh.lightning.bean.test;

public class Apple {
	private Orange orange1;
	private Orange orange2;

	@Override
	public String toString() {
		return "apple";
	}
	
	public Orange getOrange1() {
		return this.orange1;
	}
	public Orange getOrange2() {
		return this.orange2;
	}
}
