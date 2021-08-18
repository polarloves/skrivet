package com.skrivet.plugins.security.core.holder;

public class FilterHolder {
    public static final String EXCEPTION_KEY = "EXCEPTION";
    private static ThreadLocal<Exception> ex = new ThreadLocal<>();
    private static ThreadLocal<String> currentIdentify = new ThreadLocal<>();

    public static Exception getException() {
        return ex.get();
    }

    public static void setException(Exception ex) {
        FilterHolder.ex.set(ex);
    }

    public static String getCurrentIdentify() {
        return currentIdentify.get();
    }

    public static void setCurrentIdentify(String identify) {
        FilterHolder.currentIdentify.set(identify);
    }

    public static void clear() {
        if(ex.get()!=null){
            ex.remove();
        }
        if(currentIdentify.get()!=null){
            currentIdentify.remove();
        }
    }
}
