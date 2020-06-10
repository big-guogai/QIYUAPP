package com.xuhao.model;

public class CourseListBean {
    private String imgUrl;
    private String title;
    private int courseId;

    public CourseListBean() {
    }

    @Override
    public String toString() {
        return "CourseListBean{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ",courseId=" + courseId + '}';
    }

    public CourseListBean(String imgUrl, String title, int courseId) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.courseId = courseId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
