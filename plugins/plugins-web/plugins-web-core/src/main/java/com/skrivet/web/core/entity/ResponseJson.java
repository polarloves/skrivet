package com.skrivet.web.core.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用的返回数据实体类
 *
 * @author n
 * @version 1.0
 */
@ApiModel(description = "通用响应")
public class ResponseJson<T> implements Serializable {
    @ApiModelProperty(value = "响应代码", example = "000000")
    private String code;
    @ApiModelProperty(value = "响应数据")
    private T data;
    @ApiModelProperty(value = "描述信息", example = "成功")
    private String msg;
    @ApiModelProperty(value = "返回数据条目数", example = "0")
    private Long count;

    public ResponseJson(String code) {
        this(code, null, null);
    }

    public ResponseJson(String code, T data) {
        this(code, data, null);
    }

    public ResponseJson(String code, T data, String msg) {
        this(code, data, msg, null);
    }

    public ResponseJson(String code, T data, String msg, Long count) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


}
