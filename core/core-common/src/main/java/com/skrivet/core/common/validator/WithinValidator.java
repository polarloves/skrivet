package com.skrivet.core.common.validator;

import com.skrivet.core.common.annotations.Within;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WithinValidator implements ConstraintValidator<Within, Object> {
    String[] values = null;

    @Override
    public void initialize(Within constraintAnnotation) {
        values = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null){
            return true;
        }
        if (values != null && values.length > 0) {
            for (String value : values) {
                if (value.equals(String.valueOf(s))) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}
