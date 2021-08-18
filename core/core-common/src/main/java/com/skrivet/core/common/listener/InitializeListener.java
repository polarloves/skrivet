package com.skrivet.core.common.listener;

import org.springframework.context.ApplicationContext;

public interface InitializeListener {
    public void onInitialize(ApplicationContext context);
    public int sort();
}
