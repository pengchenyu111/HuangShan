package com.example.huangshan.bean;

import java.util.Date;


public class DailyNum {

    private String dataName;
    private String dataWeek;
    private int totalNum;
    private int eightNum;
    private int NineNum;

    public DailyNum() {
    }

    public DailyNum(String dataName, String dataWeek, int totalNum, int eightNum, int nineNum) {
        this.dataName = dataName;
        this.dataWeek = dataWeek;
        this.totalNum = totalNum;
        this.eightNum = eightNum;
        NineNum = nineNum;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getDataWeek() {
        return dataWeek;
    }

    public void setDataWeek(String dataWeek) {
        this.dataWeek = dataWeek;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getEightNum() {
        return eightNum;
    }

    public void setEightNum(int eightNum) {
        this.eightNum = eightNum;
    }

    public int getNineNum() {
        return NineNum;
    }

    public void setNineNum(int nineNum) {
        NineNum = nineNum;
    }

    @Override
    public String toString() {
        return "DailyNum{" +
                "dataName='" + dataName + '\'' +
                ", dataWeek='" + dataWeek + '\'' +
                ", totalNum=" + totalNum +
                ", eightNum=" + eightNum +
                ", NineNum=" + NineNum +
                '}';
    }
}
