package com.skrivet.web.core.entity;

import com.skrivet.core.common.enums.Code;
import com.skrivet.core.toolkit.CodeToolkit;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseBuilder<T> {

    private String code;
    private String message;
    private T data;
    private Long count;
    //消息变量数据
    private List<Serializable> variables = new ArrayList<>();
    //消息模板
    private String messageTemplate;

    public ResponseJson<T> build() {
        if (StringUtils.isEmpty(message)) {
            Serializable[] variables = null;
            if (!CollectionUtils.isEmpty(this.variables)) {
                variables = new Serializable[this.variables.size()];
                variables = this.variables.toArray(variables);
            }
            this.message = CodeToolkit.render(code, messageTemplate, variables);
        }
        return new ResponseJson<T>(code, data, message, count);
    }


    public ResponseBuilder<T> addVariable(Serializable value) {
        this.variables.add(value);
        return this;
    }

    public ResponseBuilder<T> addVariables(Serializable[] values) {
        if(values!=null){
            for (Serializable value : values) {
                this.variables.add(value);
            }
        }
        return this;
    }

    public ResponseBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseBuilder<T> code(String code) {
        this.code = code;
        return this;
    }

    public ResponseBuilder<T> count(Long count) {
        this.count = count;
        return this;
    }

    public ResponseBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResponseBuilder<T> template(String template) {
        this.messageTemplate = template;
        return this;
    }

    public static <T> ResponseJson<T> success() {
        ResponseBuilder<T> builder = new ResponseBuilder<T>();
        builder.code(Code.SUCCESS.getCode());
        return builder.build();
    }


    public static <T> ResponseJson<T> success(T data) {
        ResponseBuilder<T> builder = new ResponseBuilder<T>();
        builder.code(Code.SUCCESS.getCode());
        builder.data = data;
        return builder.build();
    }

    public static <T> ResponseJson<T> success(T data, Long count) {
        ResponseBuilder<T> builder = new ResponseBuilder<T>();
        builder.code(Code.SUCCESS.getCode());
        builder.data = data;
        builder.count = count;
        return builder.build();
    }

    public static <T> ResponseJson<T> success(T data, Long count, String message) {
        ResponseBuilder<T> builder = new ResponseBuilder<T>();
        builder.code(Code.SUCCESS.getCode());
        builder.data = data;
        builder.count = count;
        builder.message = message;
        return builder.build();
    }

    public static <T> ResponseJson<T> error(String code) {
        ResponseBuilder<T> builder = new ResponseBuilder<T>();
        builder.code(code);
        return builder.build();
    }

    public static <T> ResponseJson<T> error(String code, String message) {
        ResponseBuilder<T> builder = new ResponseBuilder<T>();
        builder.code(code);
        builder.message(message);
        return builder.build();
    }

}
