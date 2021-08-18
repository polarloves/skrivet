package com.skrivet.core.common.listener;


public interface SkrivetEventListenerAdapter {
    public void onEvent(String id);

    public boolean supports(String name, String action);
}
