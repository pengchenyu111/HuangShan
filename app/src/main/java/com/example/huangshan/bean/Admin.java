package com.example.huangshan.bean;

import java.io.Serializable;

public class Admin implements Serializable {
    private String adminAccount;
    private String adminPassword;
    private String adminName;
    private String adminSex;
    private int adminAge;
    private int adminWorkYear;
    private String adminPhone;
    private String adminIntroduction;
    private int adminPower;

    public Admin() {
    }

    public Admin(String adminAccount, String adminPassword, String adminName, String adminSex, int adminAge, int adminWorkYear, String adminPhone, String adminIntroduction, int adminPower) {
        this.adminAccount = adminAccount;
        this.adminPassword = adminPassword;
        this.adminName = adminName;
        this.adminSex = adminSex;
        this.adminAge = adminAge;
        this.adminWorkYear = adminWorkYear;
        this.adminPhone = adminPhone;
        this.adminIntroduction = adminIntroduction;
        this.adminPower = adminPower;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminSex() {
        return adminSex;
    }

    public void setAdminSex(String adminSex) {
        this.adminSex = adminSex;
    }

    public int getAdminAge() {
        return adminAge;
    }

    public void setAdminAge(int adminAge) {
        this.adminAge = adminAge;
    }

    public int getAdminWorkYear() {
        return adminWorkYear;
    }

    public void setAdminWorkYear(int adminWorkYear) {
        this.adminWorkYear = adminWorkYear;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminIntroduction() {
        return adminIntroduction;
    }

    public void setAdminIntroduction(String adminIntroduction) {
        this.adminIntroduction = adminIntroduction;
    }

    public int getAdminPower() {
        return adminPower;
    }

    public void setAdminPower(int adminPower) {
        this.adminPower = adminPower;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminAccount='" + adminAccount + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminSex='" + adminSex + '\'' +
                ", adminAge=" + adminAge +
                ", adminWorkYear=" + adminWorkYear +
                ", adminPhone='" + adminPhone + '\'' +
                ", adminIntroduction='" + adminIntroduction + '\'' +
                ", adminPower=" + adminPower +
                '}';
    }
}
