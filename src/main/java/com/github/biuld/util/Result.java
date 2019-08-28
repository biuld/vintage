package com.github.biuld.util;

import lombok.Data;

/**
 * Created by biuld on 2019/8/20.
 */
@Data
public class Result<T> {

    private Integer code;// 操作状态
    private String msg;// 返回信息
    private T data;// 返回数据

    public enum ErrCode {

        JWT_ERRCODE_NULL(4000, "Token does not exists"),
        JWT_ERRCODE_EXPIRE(4001, "Token is expired"),
        JWT_ERRCODE_FAIL(4002, "Wrong Token"),
        USER_NOT_FOUND(4003, "Wrong username or password"),
        USER_EXISTS(4004, "Your username or e-mail address has been taken"),

        VERIFIED_FAIlED(5000, "Verified failed, try later"),
        INPUT_ERROR(5001, "输入有误或权限不足"),
        PWD_NOT_MATCH(5002, "old password does not match"),

        ROLE_EXISTS(6000, "This role already exists"),
        TAG_EXISTS(7000, "This tag already exits");

        private Integer code;
        private String msg;

        ErrCode(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return this.msg;
        }

        public Integer getCode() {
            return this.code;
        }
    }

    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.msg = msg;
        result.data = data;
        return result;
    }

    public static Result<String> success() {
        return success("success!", null);
    }

    public static Result<String> success(String msg) {
        return success(msg, null);
    }

    public static <T> Result<T> error(ErrCode errCode) {
        Result<T> result = new Result<>();
        result.code = errCode.code;
        result.msg = errCode.msg;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + "\"" +
                ", \"data\":\"" + data + "\"" +
                '}';
    }
}

