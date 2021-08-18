package com.skrivet.plugins.queue.kafka.publish;

import com.skrivet.core.common.entity.ValueWrapper;
import com.skrivet.plugins.queue.core.publish.Publisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.Serializable;

public class KafkaPublish implements Publisher {
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String channel, ValueWrapper data, Mode mode) {
        //发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send( channel, data);
    }

    public void setKafkaTemplate(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
