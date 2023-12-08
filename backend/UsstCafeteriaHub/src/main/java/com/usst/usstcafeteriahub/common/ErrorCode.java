package com.usst.usstcafeteriahub.common;

/**
 * 全局错误码
 * @author 李英杰
 */
public enum ErrorCode {
    // 常用HTTP状态码及其描述为参考
    // 实际代码中的错误码可以根据需求进行调整

    SUCCESS(200, "成功", "操作成功。"),
    BAD_REQUEST(400, "请求错误", "服务器不能或不会处理该请求。"),
    UNAUTHORIZED(401, "未授权", "缺少或无效的认证令牌。"),
    FORBIDDEN(403, "禁止访问", "您没有权限访问此资源。"),
    NOT_FOUND(404, "未找到", "未找到请求的资源。"),
    METHOD_NOT_ALLOWED(405, "方法不被允许", "此资源不允许使用HTTP方法。"),
    UNPROCESSABLE_ENTITY(422, "不可处理的实体", "请求格式正确，但由于语义错误无法跟随。"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误", "服务器遇到错误，无法完成您的请求。"),
    BAD_GATEWAY(502, "网关错误", "服务器从上游服务器收到无效响应。"),
    SERVICE_UNAVAILABLE(503, "服务不可用", "服务器当前不可用。"),

    PARAMS_ERROR(1001, "参数错误", "参数错误。");

    private final int code;
    /**
     * 错误码信息
     */
    private final String message;
    /**
     * 详情（Data）
     */
    private final String data;

    ErrorCode(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}

