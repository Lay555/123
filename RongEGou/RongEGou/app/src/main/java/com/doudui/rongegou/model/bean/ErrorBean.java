package com.doudui.rongegou.model.bean;

/**
 * Created by Administrator on 2020/5/9.
 */
public class ErrorBean {

    private int O000000o;
    private int code;
    private String message;

    public void setO000000o(int o000000o) {
        O000000o = o000000o;
    }

    public int getO000000o() {
        return O000000o;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
