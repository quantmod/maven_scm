package foo.bar.annotation;

import java.lang.annotation.*;

/**
 * Created by pengli on 2015/5/12.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fooish {

    boolean cool() default true;

    String[] tags() default {"all"};
}
