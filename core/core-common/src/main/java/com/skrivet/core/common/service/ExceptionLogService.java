package com.skrivet.core.common.service;

/**
 * 异常日志记录服务
 */
public interface ExceptionLogService {
    public void addLog(String tag, Exception ex);
}
