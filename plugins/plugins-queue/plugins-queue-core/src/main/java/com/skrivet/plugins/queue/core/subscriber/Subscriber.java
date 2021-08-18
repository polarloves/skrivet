package com.skrivet.plugins.queue.core.subscriber;

import com.skrivet.plugins.queue.core.listener.Listener;

import java.io.Serializable;

/**
 * 订阅器
 *
 * @author n
 * @version 1.0
 */
public interface Subscriber {
    /**
     * 订阅频道，当频道中有数据时，调用数据监听器
     *
     * @param channel  频道
     * @param listener 监听器
     */
    public void registerSubscribe(String channel, Listener listener);
}
