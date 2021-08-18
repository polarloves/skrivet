package com.skrivet.components.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserNamePasswordToken extends UsernamePasswordToken {
    private boolean validate;

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }
}
