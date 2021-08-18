package com.skrivet.core.common.listener;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ApplicationListener implements org.springframework.context.ApplicationListener<ApplicationReadyEvent> {
    @Autowired(required = false)
    private Map<String, InitializeListener> listeners;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (listeners != null) {
            List<InitializeListener> sortListeners = new ArrayList<InitializeListener>();
            sortListeners.addAll(listeners.values());
            Collections.sort(sortListeners, new Comparator<InitializeListener>() {
                @Override
                public int compare(InitializeListener o1, InitializeListener o2) {
                    return o1.sort() - o2.sort();
                }
            });
            for (InitializeListener bean : sortListeners) {
                bean.onInitialize(applicationReadyEvent.getApplicationContext());
            }
        }
    }
}
