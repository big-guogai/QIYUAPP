package com.xuhao.myapp.Bean;

public class BossCourseInfoBean {

    private int courseId;
    private int bossId;
    private String courseName;
    private String courseInformation;
    private double coursePrice;
    private String coursePriceType;
    private String courseTypeId1;
    private String courseTypeId2;
    private String courseTypeId3;

    @Override
    public String toString() {
        return "BossCourseInfoBean{" +
                "courseId=" + courseId +
                ", bossId=" + bossId +
                ", courseName='" + courseName + '\'' +
                ", courseInformation='" + courseInformation + '\'' +
                ", coursePrice=" + coursePrice +
                ", coursePriceType='" + coursePriceType + '\'' +
                ", courseTypeId1='" + courseTypeId1 + '\'' +
                ", courseTypeId2='" + courseTypeId2 + '\'' +
                ", courseTypeId3='" + courseTypeId3 + '\'' +
                '}';
    }

    public int getBossId() {
        return bossId;
    }

    public void setBossId(int bossId) {
        this.bossId = bossId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseInformation(String courseInformation) {
        this.courseInformation = courseInformation;
    }

    public void setCoursePrice(double coursePrice) {
        this.coursePrice = coursePrice;
    }

    public void setCoursePriceType(String coursePriceType) {
        this.coursePriceType = coursePriceType;
    }

    public void setCourseTypeId1(String courseTypeId1) {
        this.courseTypeId1 = courseTypeId1;
    }

    public void setCourseTypeId2(String courseTypeId2) {
        this.courseTypeId2 = courseTypeId2;
    }

    public void setCourseTypeId3(String courseTypeId3) {
        this.courseTypeId3 = courseTypeId3;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseInformation() {
        return courseInformation;
    }

    public double getCoursePrice() {
        return coursePrice;
    }

    public String getCoursePriceType() {
        return coursePriceType;
    }

    public String getCourseTypeId1() {
        return courseTypeId1;
    }

    public String getCourseTypeId2() {
        return courseTypeId2;
    }

    public String getCourseTypeId3() {
        return courseTypeId3;
    }
}
