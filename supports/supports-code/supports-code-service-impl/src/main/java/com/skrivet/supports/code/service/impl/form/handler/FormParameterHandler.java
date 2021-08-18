package com.skrivet.supports.code.service.impl.form.handler;

import java.util.Map;

public interface FormParameterHandler {
    public void process(Map<String, Object> value);

    public int order();
}
