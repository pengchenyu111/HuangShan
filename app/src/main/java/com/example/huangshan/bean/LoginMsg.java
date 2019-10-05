package com.example.huangshan.bean;

/**
 * 用于处理管理员登陆请求，服务器返回的json数据对应的实体类
 */
public class LoginMsg {
//    返回码
    private String resCode;
//    返回消息
    private String resMsg;


    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
}
