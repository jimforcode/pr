package cn.creatoo.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    public static Object getBean(String name)
            throws BeansException {
        return applicationContext.getBean(name);
    }
    public static Object getBean(String name, Class requiredType)
            throws BeansException {

        return applicationContext.getBean(name, requiredType);
    }
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }
}
