package com.skrivet.plugins.queue.core.annotations;

import com.skrivet.plugins.queue.core.publish.Publisher;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Publish {
    enum Opportunity {
        BEFORE, AFTER
    }

    Opportunity opportunity() default Opportunity.AFTER;

    String channel();

    boolean channelUseExpression() default false;

    String value() default "";

    boolean valueUseExpression() default true;

    Publisher.Mode mode() default Publisher.Mode.BROADCAST;
}
