package cn.fh.lightning.mvc.exception;

public class InvalidControllerException extends RuntimeException {
	public InvalidControllerException(String msg) {
		super(msg);
	}
}
