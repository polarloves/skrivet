package com.skrivet.core.toolkit.listener;

import com.skrivet.core.common.convert.CodeConvert;
import com.skrivet.core.common.listener.InitializeListener;
import com.skrivet.core.toolkit.CodeToolkit;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CodeListener implements InitializeListener {
    @Override
    public void onInitialize(ApplicationContext context) {
        CodeToolkit.setCodeConvert(context.getBean(CodeConvert.class));
    }

    @Override
    public int sort() {
        return 0;
    }
}
