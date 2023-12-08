package com.usst.usstcafeteriahub.common;

import java.util.Map;

/**
 * 返回结果封装类
 * @author 李英杰
 */
public class Result {

    /**
     * 成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), data, ErrorCode.SUCCESS.getMessage());
    }


    public static <T> BaseResponse<T> success(T data, String message) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), data, message);
    }

//    public static <T> BaseResponse<T> success(ErrorCode errorCode, String message) {
//        return new BaseResponse<>(errorCode.getCode(), null, message);
//    }



    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), null, message);
    }


    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage());
    }

    /**
     * 失败，自定义消息
     * @param errorCode
     * @param message
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }

    /**
     * 自定义错误
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }


    /**
     * 失败
     * @param message
     * @return
     */
    public static BaseResponse error(String message) {
        return new BaseResponse<>(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), null, message);
    }





}
