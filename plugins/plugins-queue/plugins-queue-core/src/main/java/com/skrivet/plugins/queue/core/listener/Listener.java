package com.skrivet.plugins.queue.core.listener;

import java.io.Serializable;

/**
 * 订阅监听器
 *
 * @author n
 * @version 1.0
 */
public interface Listener {
    /**
     * 数据接收的回调
     *
     * @param channel 频道
     * @param data    数据
     */
    public void onDataReceived(Serializable channel, Serializable data);
}
