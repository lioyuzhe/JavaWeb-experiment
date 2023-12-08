package com.usst.usstcafeteriahub.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 通用返回类
 * @param <T>
 * @author 李英杰
 */
@Data
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {

    private int code;
    private T data;
    private String message;


    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), (T) null, errorCode.getMessage());
    }

    public BaseResponse(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), (T) null, message);
    }

    public BaseResponse() {
        this(ErrorCode.SUCCESS.getCode(), (T) null, ErrorCode.SUCCESS.getMessage());
    }

    public BaseResponse(T data) {
        this(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMessage());
    }





}
