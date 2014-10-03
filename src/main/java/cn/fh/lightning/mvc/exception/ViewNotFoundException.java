package cn.fh.lightning.mvc.exception;

public class ViewNotFoundException extends RuntimeException {
	public ViewNotFoundException(String msg) {
		super(msg);
	}
}
