package com.skrivet.plugins.queue.kafka.subscriber;

import com.skrivet.core.common.entity.ValueWrapper;
import com.skrivet.plugins.queue.core.listener.Listener;
import com.skrivet.plugins.queue.core.subscriber.Subscriber;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KafkaSubscriber implements Subscriber {
    private KafkaConsumer<String, Object> consumer;
    private SubscribeThread thread;
    private List<String> topics = new ArrayList<>();

    @Override
    public void registerSubscribe(String channel, Listener listener) {
        topics.add((String) channel);
        consumer.unsubscribe();
        consumer.subscribe(topics);
        if (thread == null) {
            thread = new SubscribeThread(consumer);
            thread.start();
        }
        thread.addListener((String) channel, listener);
    }

    public void setConsumer(KafkaConsumer<String, Object> consumer) {
        this.consumer = consumer;
    }

    private static class SubscribeThread extends Thread {
        private Map<String, Listener> listenerMap = new HashMap<>();
        private KafkaConsumer<String, Object> consumer;

        public SubscribeThread(KafkaConsumer<String, Object> consumer) {
            this.consumer = consumer;
        }

        public void addListener(String key, Listener listener) {
            listenerMap.put(key, listener);
        }

        @Override
        public void run() {
            while (true) {
                ConsumerRecords<String, Object> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, Object> record : records) {
                    if (listenerMap.get(record.key()) != null) {
                        ValueWrapper valueWrapper= (ValueWrapper) record.value();
                        listenerMap.get(record.key()).onDataReceived(record.key(), valueWrapper.get());
                    }
                }
            }
        }
    }
}
