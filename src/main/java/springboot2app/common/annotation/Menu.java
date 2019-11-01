package springboot2app.common.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Menu {
    String method() default "GET";

    String uri();

    String mod();

    String sub();

    String name();
}