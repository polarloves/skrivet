package com.skrivet.plugins.database.mongodb.annotations;

import com.skrivet.plugins.database.mongodb.enums.MatchStyle;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {
    MatchStyle value() default MatchStyle.EQUAL;
}
