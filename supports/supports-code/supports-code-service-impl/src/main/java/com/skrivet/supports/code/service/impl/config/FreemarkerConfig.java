package com.skrivet.supports.code.service.impl.config;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.*;
@ImportAutoConfiguration(exclude = {FreeMarkerAutoConfiguration.class})
public class FreemarkerConfig {
}
