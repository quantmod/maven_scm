package foo.bar.annotation;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by pengli on 2015/5/12.
 */
@Component
public class MyFooishHandler implements ApplicationContextAware, InitializingBean {

    private Logger logger = Logger.getLogger(MyFooishHandler.class);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final Map<String, Object> myFoos = applicationContext.getBeansWithAnnotation(Fooish.class);

        for (final Object myFoo : myFoos.values()) {
            final Class<? extends Object> fooClass = myFoo.getClass();
            final Fooish annotation = fooClass.getAnnotation(Fooish.class);
            logger.info("Found foo class: " + fooClass + ", with tags: " + Arrays.toString(annotation.tags()));
        }
    }
}
