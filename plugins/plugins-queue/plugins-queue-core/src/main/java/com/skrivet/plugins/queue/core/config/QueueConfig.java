package com.skrivet.plugins.queue.core.config;

import com.skrivet.plugins.queue.core.aop.PublishAnnotationAdvisor;
import com.skrivet.plugins.queue.core.publish.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    @Autowired(required = false)
    private Publisher publisher;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public PublishAnnotationAdvisor publishAnnotationAdvisor() {
        if (publisher == null) {
            logger.warn("you have not config Publisher,the distributed system may have some errors!");
        }
        PublishAnnotationAdvisor publishAnnotationAdvisor = new PublishAnnotationAdvisor(publisher);
        return publishAnnotationAdvisor;
    }
}
