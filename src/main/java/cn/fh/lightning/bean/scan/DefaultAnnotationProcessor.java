package cn.fh.lightning.bean.scan;

import cn.fh.lightning.bean.BeanUtil;
import cn.fh.lightning.bean.annotation.Bean;
import cn.fh.lightning.bean.annotation.Dependency;
import cn.fh.lightning.bean.container.BeanContainer;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Analyze all annotations in target class and register that class
 * to container.
 *
 * Created by whf on 15-2-27.
 */
public class DefaultAnnotationProcessor implements Runnable {
    private Class clazz;
    private BeanContainer container;

    private Map<String, String> propMap = new HashMap<>();

    /**
     * Construct a new instance.
     *
     * @param clazz The Class object of the class you want to analyze annotations for.
     * @param container The IoC container.
     * @param newThread If you are going to use this class in a separate thread, you must set this flag to true.
     */
    public DefaultAnnotationProcessor(Class clazz, BeanContainer container, boolean newThread) {
        this.clazz = clazz;
        this.container = container;

        if (newThread) {
            doProcess();
        }
    }

    @Override
    public void run() {
        doProcess();
    }

    private void doProcess() {
        if (clazz.isAnnotationPresent(Bean.class)) {
            processBeanAnnotation();
        }
    }

    private void processBeanAnnotation() {
        // get bean name
        Bean beanAn = (Bean) clazz.getAnnotation(Bean.class);
        String beanName = beanAn.value();

        // check @Dependency annotation
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // process @Dependency annotation if any.
            if (field.isAnnotationPresent(Dependency.class)) {
                processDependencyAnnotation(field);
            }
        }

        // create implementation of Bean interface
        cn.fh.lightning.bean.Bean bean = BeanUtil.createSingletonBean(beanName, clazz, propMap);
        // register Bean to IoC container
        container.registerBean(bean);
    }

    private void processDependencyAnnotation(Field field) {
        Dependency depAn = (Dependency) field.getAnnotation(Dependency.class);
        boolean required = depAn.required();

        // get bean name it depends
        String depBeanName = depAn.name();
        // get field name
        String fieldName = field.getName();

        propMap.put(fieldName, depBeanName);
    }

}
