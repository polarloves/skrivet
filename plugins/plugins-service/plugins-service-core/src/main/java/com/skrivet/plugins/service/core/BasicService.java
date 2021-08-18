package com.skrivet.plugins.service.core;

import com.skrivet.core.common.convert.EntityConvert;
import com.skrivet.core.common.convert.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class BasicService {
    @Value("${skrivet.project.name}")
    public String projectName;
    @Autowired
    public EntityConvert entityConvert;
    @Autowired
    public IdGenerator idGenerator;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected <T> T current(Class<T> clz) {
        return (T) AopContext.currentProxy();
    }
}
