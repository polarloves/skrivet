package com.skrivet.core.common.annotations;


import com.skrivet.core.common.validator.WithinValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 在**之内的注解，标识值必须在value之内
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = WithinValidator.class
)
public @interface Within {
    String message() default "illegal parameter";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    public String[] value();

}
