package com.skrivet.web.core.annotations;

/**
 * 日志写入策略
 *
 * @author n
 * @version 1.0
 */
public enum LogPolicy {
    /**
     * ACCESS_ONLY: 仅写入访问日志
     * ERROR_ONLY: 仅当出错时写入日志
     * BOTH: 出错/访问时均写入日志
     * NONE: 不写入日志
     */
    ACCESS_ONLY(0b01), ERROR_ONLY(0b10), BOTH(0b11), NONE(0);

    private int policy;

    LogPolicy(int policy) {
        this.policy = policy;
    }

    public int Policy() {
        return policy;
    }

    public int getPolicy() {
        return policy;
    }
}
