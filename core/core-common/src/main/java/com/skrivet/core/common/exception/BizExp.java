package com.skrivet.core.common.exception;


import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 框架异常，基础异常。
 *
 * @author n
 * @version 1.0
 */
public class BizExp extends RuntimeException {
    private static final long serialVersionUID = -2706346606153075352L;
    private String code;
    private String tip;
    private Serializable[] args;
    private String realClassName;

    public void setMessage(String message) {
        try {
            Field field = Throwable.class.getDeclaredField("detailMessage");
            field.setAccessible(true);
            field.set(this, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRealClassName(String clzName) {
        this.realClassName = clzName;
    }

    public void setCause(Throwable throwable) {
        try {
            Field field = Throwable.class.getDeclaredField("cause");
            field.setAccessible(true);
            field.set(this, throwable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BizExp(String code) {
        super();
        this.code = code;
    }

    public BizExp(String code, String tip) {
        super();
        this.code = code;
        this.tip = tip;
    }

    public BizExp variables(Serializable[] variables) {
        this.args = variables;
        return this;
    }

    public BizExp variable(Serializable variable) {
        this.args = new Serializable[1];
        this.args[0] = variable;
        return this;
    }

    @Override
    public String toString() {
        String s = realClassName == null ? getClass().getName() : realClassName;
        String message = getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

    public BizExp copyStackTrace(Throwable e) {
        if(e==null){
            return this;
        }
        if (e instanceof BizExp) {
            return (BizExp) e;
        }
        this.setMessage(e.getMessage());
        this.setRealClassName(e.getClass().getCanonicalName());
        this.setStackTrace(e.getStackTrace());
        if (e.getCause() != null) {
            BizExp cause = new BizExp(null);
            this.setCause(cause.copyStackTrace(e.getCause()));
        }
        return this;
    }

    public Serializable[] getArgs() {
        return args;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
