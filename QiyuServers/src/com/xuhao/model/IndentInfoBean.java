package com.xuhao.model;

import java.sql.Timestamp;

public class IndentInfoBean {
    private String username;
    private int indentid;
    private long buyuserid;
    private int bossid;
    private int courseid;
    private double courseunitprice;
    private int coursequantity;
    private double indentprice;
    private Timestamp indenttime;
    private String indenttype;
    private String bossname;
    private String coursename;
    private String coursePriceType;
    private long bossTelePhone;

    @Override
    public String toString() {
        return "IndentInfoBean{" +
                "username='" + username + '\'' +
                ", indentid=" + indentid +
                ", buyuserid=" + buyuserid +
                ", bossid=" + bossid +
                ", courseid=" + courseid +
                ", courseunitprice=" + courseunitprice +
                ", coursequantity=" + coursequantity +
                ", indentprice=" + indentprice +
                ", indenttime=" + indenttime +
                ", indenttype='" + indenttype + '\'' +
                ", bossname='" + bossname + '\'' +
                ", coursename='" + coursename + '\'' +
                ", coursePriceType='" + coursePriceType + '\'' +
                ", bossTelePhone=" + bossTelePhone +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getIndenttime() {
        return indenttime;
    }

    public void setIndenttime(Timestamp indenttime) {
        this.indenttime = indenttime;
    }

    public long getBossTelePhone() {
        return bossTelePhone;
    }

    public void setBossTelePhone(long bossTelePhone) {
        this.bossTelePhone = bossTelePhone;
    }

    public String getCoursePriceType() {
        return coursePriceType;
    }

    public void setCoursePriceType(String coursePriceType) {
        this.coursePriceType = coursePriceType;
    }

    public String getBossname() {
        return bossname;
    }

    public void setBossname(String bossname) {
        this.bossname = bossname;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getIndentid() {
        return indentid;
    }

    public void setIndentid(int indentid) {
        this.indentid = indentid;
    }

    public long getBuyuserid() {
        return buyuserid;
    }

    public void setBuyuserid(long buyuserid) {
        this.buyuserid = buyuserid;
    }

    public int getBossid() {
        return bossid;
    }

    public void setBossid(int bossid) {
        this.bossid = bossid;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public double getCourseunitprice() {
        return courseunitprice;
    }

    public void setCourseunitprice(double courseunitprice) {
        this.courseunitprice = courseunitprice;
    }

    public int getCoursequantity() {
        return coursequantity;
    }

    public void setCoursequantity(int coursequantity) {
        this.coursequantity = coursequantity;
    }

    public double getIndentprice() {
        return indentprice;
    }

    public void setIndentprice(double indentprice) {
        this.indentprice = indentprice;
    }

    public String getIndenttype() {
        return indenttype;
    }

    public void setIndenttype(String indenttype) {
        this.indenttype = indenttype;
    }
}
