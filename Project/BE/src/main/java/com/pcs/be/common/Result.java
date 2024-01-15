package com.pcs.be.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private Integer code; // 响应码——200成功，400失败
    private String msg;  // 响应请求的具体信息
    private T data; // 传输数据


    private Integer id; // 用户登录后给前端传id
    private Integer role; // 1-管理员，2-用户，3-商家

    public Result() {
    }

    public void getData(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void succ(T data) {
        getData(200, "操作成功", data);
    }

    public void fail(String msg) {
        getData(400, msg, null);
    }

}
