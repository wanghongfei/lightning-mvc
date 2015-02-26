package cn.fh.lightning.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by whf on 15-2-26.
 */
@Target(ElementType.TYPE)
public @interface Bean {
    public String value();
}
