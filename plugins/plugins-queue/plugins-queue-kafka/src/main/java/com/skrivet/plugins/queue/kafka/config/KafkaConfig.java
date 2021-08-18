package com.skrivet.plugins.queue.kafka.config;

import com.skrivet.plugins.queue.core.publish.Publisher;
import com.skrivet.plugins.queue.core.subscriber.Subscriber;
import com.skrivet.plugins.queue.kafka.publish.KafkaPublish;
import com.skrivet.plugins.queue.kafka.subscriber.KafkaSubscriber;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(value = "skrivet.queue", havingValue = "kafka")
@Import({ KafkaAutoConfiguration.class })
public class KafkaConfig {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private KafkaConsumer<String, Object> consumer;

    @Bean
    public Publisher publisher() {
        KafkaPublish kafkaPublish = new KafkaPublish();
        kafkaPublish.setKafkaTemplate(kafkaTemplate);
        return kafkaPublish;
    }

    @Bean
    public Subscriber subscriber() {
        KafkaSubscriber subscriber = new KafkaSubscriber();
        subscriber.setConsumer(consumer);
        return subscriber;
    }
}
