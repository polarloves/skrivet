package com.skrivet.plugins.web.impl.handler;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.common.exception.BizExp;
import com.skrivet.core.common.exception.IgnoreLoggedExp;
import com.skrivet.core.common.service.ExceptionLogService;
import com.skrivet.core.toolkit.CollectionUtils;
import com.skrivet.web.core.annotations.LogPolicy;
import com.skrivet.web.core.annotations.ResMsg;
import com.skrivet.web.core.entity.ResponseBuilder;
import com.skrivet.web.core.entity.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
    @Autowired(required = false)
    private ExceptionLogService exceptionLogService;

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public ResponseJson handlerException(ConstraintViolationException ex) {
        //数据校验异常
        return new ResponseBuilder().code(Code.COMMON_VALIDATION.getCode()).message(CollectionUtils.asString(ex.getConstraintViolations(), o1 -> {
            return o1.getMessage();
        })).build();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseJson handlerException(MissingServletRequestParameterException ex) {
        //数据中某个项不能为空的异常
        return new ResponseBuilder().code(Code.COMMON_NOT_NULL.getCode()).addVariable(ex.getParameterName()).build();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public ResponseJson handlerException(BindException ex) {
        //数据校验异常
        List<FieldError> error = ex.getBindingResult().getFieldErrors();
        return new ResponseBuilder().code(Code.COMMON_VALIDATION.getCode()).message(CollectionUtils.asString(error, o1 -> {
            return o1.getDefaultMessage();
        })).build();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseJson handlerException(MethodArgumentNotValidException ex) {
        //数据校验异常
        List<FieldError> error = ex.getBindingResult().getFieldErrors();
        return new ResponseBuilder().code(Code.COMMON_VALIDATION.getCode()).message(CollectionUtils.asString(error, o1 -> {
            return o1.getDefaultMessage();
        })).build();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BizExp.class)
    public ResponseJson handlerBizExp(HandlerMethod handlerMethod, BizExp ex) {
        ResMsg resMsg = handlerMethod.getMethodAnnotation(ResMsg.class);
        writeLog(resMsg, ex);
        if (!StringUtils.isEmpty(ex.getTip())) {
            return new ResponseBuilder().code(ex.getCode()).message(ex.getTip()).build();
        }
        return new ResponseBuilder().code(ex.getCode()).addVariables(ex.getArgs()).build();
    }

    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseJson handlerException(HandlerMethod handlerMethod, Exception ex) {
        if (ex.getCause() != null && ex.getCause() instanceof BizExp) {
            return handlerBizExp(handlerMethod, (BizExp) ex.getCause());
        }
        ResMsg resMsg = handlerMethod.getMethodAnnotation(ResMsg.class);
        writeLog(resMsg, ex);
        return new ResponseBuilder().code(Code.SERVER_UNKNOWN.getCode()).build();
    }

    private void writeLog(ResMsg resMsg, Exception ex) {
        boolean log = (ex instanceof IgnoreLoggedExp|| ex instanceof org.apache.catalina.connector.ClientAbortException) ? false : true;
        if (log && resMsg != null) {
            LogPolicy logPolicy = resMsg.logPolicy();
            if ((logPolicy.getPolicy() & 0b10) == 0) {
                log = false;
            }
        }
        if (log) {
            if (exceptionLogService != null) {
                try {
                    exceptionLogService.addLog(resMsg == null ? "未知" : resMsg.tag(), ex);
                } catch (Exception e) {

                }
            }
            logger.error("未知异常,error:{}", ex);
        }
    }
}
