package com.skrivet.plugins.queue.core.publish;

import com.skrivet.core.common.entity.ValueWrapper;

import java.io.Serializable;

/**
 * 发布器
 *
 * @author n
 * @version 1.0
 */
public interface Publisher {
    enum Mode {
        BROADCAST, QUEUE
    }

    /**
     * 发布数据至指定的channel
     *
     * @param channel 发送数据的频道
     * @param data    数据
     */
    public void publish(String channel, ValueWrapper data, Mode mode);
}
