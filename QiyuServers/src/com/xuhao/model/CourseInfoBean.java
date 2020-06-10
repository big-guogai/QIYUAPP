package com.xuhao.model;

public class CourseInfoBean {
	 private int bossId;
	    private String imgUrl1;
	    private String imgUrl2;
	    private String imgUrl3;
	    private String imgUrl4;
	    private String imgUrl5;
	    private String bossName;
	    private long bossTelePhone;
	    private String bossInfomation;
	    private String bossPosition;
	    private String courseName;
	    private double coursePrice;
	    private String coursePriceType;
	    private String courseInfomation;

	    @Override
	    public String toString() {
	        return "CourseInfoBean{" +
	                "bossId=" + bossId +
	                ", imgUrl1='" + imgUrl1 + '\'' +
	                ", imgUrl2='" + imgUrl2 + '\'' +
	                ", imgUrl3='" + imgUrl3 + '\'' +
	                ", imgUrl4='" + imgUrl4 + '\'' +
	                ", imgUrl5='" + imgUrl5 + '\'' +
	                ", bossName='" + bossName + '\'' +
	                ", bossTelePhone=" + bossTelePhone +
	                ", bossInfomation='" + bossInfomation + '\'' +
	                ", bossPosition='" + bossPosition + '\'' +
	                ", courseName='" + courseName + '\'' +
	                ", coursePrice=" + coursePrice +
	                ", coursePriceType='" + coursePriceType + '\'' +
	                ", courseInfomation='" + courseInfomation + '\'' +
	                '}';
	    }

	    public int getBossId() {
	        return bossId;
	    }

	    public void setBossId(int bossId) {
	        this.bossId = bossId;
	    }

	    public CourseInfoBean() {
	    }

	    public String getBossName() {
	        return bossName;
	    }

	    public void setBossName(String bossName) {
	        this.bossName = bossName;
	    }

	    public String getCourseName() {
	        return courseName;
	    }

	    public void setCourseName(String courseName) {
	        this.courseName = courseName;
	    }

	    public double getCoursePrice() {
	        return coursePrice;
	    }

	    public void setCoursePrice(double coursePrice) {
	        this.coursePrice = coursePrice;
	    }

	    public String getCoursePriceType() {
	        return coursePriceType;
	    }

	    public void setCoursePriceType(String coursePriceType) {
	        this.coursePriceType = coursePriceType;
	    }

	    public String getCourseInfomation() {
	        return courseInfomation;
	    }

	    public void setCourseInfomation(String courseInfomation) {
	        this.courseInfomation = courseInfomation;
	    }

	    public String getImgUrl1() {
	        return imgUrl1;
	    }

	    public void setImgUrl1(String imgUrl1) {
	        this.imgUrl1 = imgUrl1;
	    }

	    public String getImgUrl2() {
	        return imgUrl2;
	    }

	    public void setImgUrl2(String imgUrl2) {
	        this.imgUrl2 = imgUrl2;
	    }

	    public String getImgUrl3() {
	        return imgUrl3;
	    }

	    public void setImgUrl3(String imgUrl3) {
	        this.imgUrl3 = imgUrl3;
	    }

	    public String getImgUrl4() {
	        return imgUrl4;
	    }

	    public void setImgUrl4(String imgUrl4) {
	        this.imgUrl4 = imgUrl4;
	    }

	    public String getImgUrl5() {
	        return imgUrl5;
	    }

	    public void setImgUrl5(String imgUrl5) {
	        this.imgUrl5 = imgUrl5;
	    }

	    public long getBossTelePhone() {
	        return bossTelePhone;
	    }

	    public void setBossTelePhone(long bossTelePhone) {
	        this.bossTelePhone = bossTelePhone;
	    }

	    public String getBossInfomation() {
	        return bossInfomation;
	    }

	    public void setBossInfomation(String bossInfomation) {
	        this.bossInfomation = bossInfomation;
	    }

	    public String getBossPosition() {
	        return bossPosition;
	    }

	    public void setBossPosition(String bossPosition) {
	        this.bossPosition = bossPosition;
	    }
}
