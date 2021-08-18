package com.skrivet.core.common.annotations;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PublishEvent {
    enum Opportunity {
        BEFORE, AFTER
    }

    Opportunity opportunity() default Opportunity.AFTER;

    String id();

    String name() default "default";

    String action() default "default";
}
