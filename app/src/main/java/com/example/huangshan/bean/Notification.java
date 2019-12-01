package com.example.huangshan.bean;

import java.io.Serializable;

public class Notification implements Serializable {

    private int notificationId;
    private String sendTime;
    private String sendAdminName;
    private String title;
    private String content;
    private int type;//type==0 客流预警；1==>天气预警；2==>优惠通知

    public Notification() {
    }

    public Notification(int notificationId, String sendTime, String sendAdminTime, String title, String content, int type) {
        this.notificationId = notificationId;
        this.sendTime = sendTime;
        this.sendAdminName = sendAdminTime;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendAdminName() {
        return sendAdminName;
    }

    public void setSendAdminName(String sendAdminName) {
        this.sendAdminName = sendAdminName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", sendTime='" + sendTime + '\'' +
                ", sendAdminName='" + sendAdminName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                '}';
    }
}
