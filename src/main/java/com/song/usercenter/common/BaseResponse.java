package com.song.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author The Outsider
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -2300736619514645930L;
    private int code;
    private T data;
    private String message;
    private String description;

    /**
     *
     * @param code
     * @param data
     * @param message
     * @param description
     */
    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    /**
     * 支持自定义状态码、数据与状态码信息
     * @param code 状态码
     * @param data 数据
     * @param message 状态码信息
     */
    public BaseResponse(int code, T data, String message) {
        this(code, data, message,  "");
    }

    /**
     * 支持自定义状态码与数据
     * @param code 状态码
     * @param data 数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "",  "");
    }

    /**
     * 支持传入错误码
     * @param errorCode
     */
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
