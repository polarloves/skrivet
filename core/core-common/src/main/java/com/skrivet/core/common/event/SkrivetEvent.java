package com.skrivet.core.common.event;

import org.springframework.context.ApplicationEvent;

public class SkrivetEvent extends ApplicationEvent {
    public SkrivetEvent(SkrivetEventSource source) {
        super(source);
    }
}
