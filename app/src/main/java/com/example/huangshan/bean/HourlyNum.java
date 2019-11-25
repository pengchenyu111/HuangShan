package com.example.huangshan.bean;

public class HourlyNum {

    private String dateName;
    private String hour;
    private int hourNum;

    public HourlyNum() {
    }

    public HourlyNum(String dateName, String hour, int hourNum) {
        this.dateName = dateName;
        this.hour = hour;
        this.hourNum = hourNum;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
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
        return "HourlyNum{" +
                "dateName='" + dateName + '\'' +
                ", hour='" + hour + '\'' +
                ", hourNum=" + hourNum +
                '}';
    }
}
