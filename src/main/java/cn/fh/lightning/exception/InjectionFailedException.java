package cn.fh.lightning.exception;

/**
 * Created by whf on 15-2-25.
 */
public class InjectionFailedException extends RuntimeException {
    public InjectionFailedException(String msg) {
        super(msg);
    }
}
