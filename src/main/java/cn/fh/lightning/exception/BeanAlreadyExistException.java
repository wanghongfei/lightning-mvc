package cn.fh.lightning.exception;

/**
 * This exception indicates that the bean you're going to put into the container
 * has already existed in container.
 */
public class BeanAlreadyExistException extends RuntimeException {
	public BeanAlreadyExistException(String msg) {
		super(msg);
	}
}
