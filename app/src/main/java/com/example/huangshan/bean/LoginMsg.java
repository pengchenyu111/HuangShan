package com.example.huangshan.bean;

/**
 * 用于处理管理员登陆请求，服务器返回的json数据对应的实体类
 */
public class LoginMsg {
//    返回码
    private String resultCode;
//    返回消息
    private String resultMessage;


    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
