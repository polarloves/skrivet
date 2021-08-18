package com.skrivet.components.orika;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaConfig {
    @Bean
    public MapperFactory mapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        return mapperFactory;
    }

    @Bean
    public OrikaEntityConvert orikaEntityConvert(MapperFactory mapperFactory) {
        OrikaEntityConvert orikaEntityConvert = new OrikaEntityConvert(mapperFactory);
        return orikaEntityConvert;
    }
}
