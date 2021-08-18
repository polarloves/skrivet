package com.skrivet.plugins.rpc.dubbo.provider;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = "classpath*:dubbo-provider.xml")
public class ProviderConfig {
}
