package com.example.huangshan.bean;

public class OneDayHourlyNum {
    private String hour;
    private int hourNum;

    public OneDayHourlyNum() {
    }

    public OneDayHourlyNum(String hour, int hourNum) {
        this.hour = hour;
        this.hourNum = hourNum;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getHourNum() {
        return hourNum;
    }

    public void setHourNum(int hourNum) {
        this.hourNum = hourNum;
    }

    @Override
    public String toString() {
        return "OneDayHourlyNum{" +
                "hour='" + hour + '\'' +
                ", hourNum=" + hourNum +
                '}';
    }
}
