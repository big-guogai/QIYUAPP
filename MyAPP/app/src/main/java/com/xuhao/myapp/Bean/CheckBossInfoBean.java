package com.xuhao.myapp.Bean;

public class CheckBossInfoBean {

    private long userId;
    private String bossName;
    private String bossDocument;
    private long bossTelePhone;
    private String bossPhotoOne;
    private String bossPhotoTwo;
    private String bossPhotoThree;
    private String bossPhotoFour;
    private String bossPhotoFive;
    private String bossInformation;
    private String bossPosition;
    private String bossReceiptType;
    private String bossReceiptId;

    @Override
    public String toString() {
        return "CheckBossInfoBean{" +
                "userId=" + userId +
                ", bossName='" + bossName + '\'' +
                ", bossDocument='" + bossDocument + '\'' +
                ", bossTelePhone=" + bossTelePhone +
                ", bossPhotoOne='" + bossPhotoOne + '\'' +
                ", bossPhotoTwo='" + bossPhotoTwo + '\'' +
                ", bossPhotoThree='" + bossPhotoThree + '\'' +
                ", bossPhotoFour='" + bossPhotoFour + '\'' +
                ", bossPhotoFive='" + bossPhotoFive + '\'' +
                ", bossInformation='" + bossInformation + '\'' +
                ", bossPosition='" + bossPosition + '\'' +
                ", bossReceiptType='" + bossReceiptType + '\'' +
                ", bossReceiptId='" + bossReceiptId + '\'' +
                '}';
    }

    public String getBossReceiptType() {
        return bossReceiptType;
    }

    public void setBossReceiptType(String bossReceiptType) {
        this.bossReceiptType = bossReceiptType;
    }

    public String getBossReceiptId() {
        return bossReceiptId;
    }

    public void setBossReceiptId(String bossReceiptId) {
        this.bossReceiptId = bossReceiptId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getBossDocument() {
        return bossDocument;
    }

    public void setBossDocument(String bossDocument) {
        this.bossDocument = bossDocument;
    }

    public long getBossTelePhone() {
        return bossTelePhone;
    }

    public void setBossTelePhone(long bossTelePhone) {
        this.bossTelePhone = bossTelePhone;
    }

    public String getBossPhotoOne() {
        return bossPhotoOne;
    }

    public void setBossPhotoOne(String bossPhotoOne) {
        this.bossPhotoOne = bossPhotoOne;
    }

    public String getBossPhotoTwo() {
        return bossPhotoTwo;
    }

    public void setBossPhotoTwo(String bossPhotoTwo) {
        this.bossPhotoTwo = bossPhotoTwo;
    }

    public String getBossPhotoThree() {
        return bossPhotoThree;
    }

    public void setBossPhotoThree(String bossPhotoThree) {
        this.bossPhotoThree = bossPhotoThree;
    }

    public String getBossPhotoFour() {
        return bossPhotoFour;
    }

    public void setBossPhotoFour(String bossPhotoFour) {
        this.bossPhotoFour = bossPhotoFour;
    }

    public String getBossPhotoFive() {
        return bossPhotoFive;
    }

    public void setBossPhotoFive(String bossPhotoFive) {
        this.bossPhotoFive = bossPhotoFive;
    }

    public String getBossInformation() {
        return bossInformation;
    }

    public void setBossInformation(String bossInformation) {
        this.bossInformation = bossInformation;
    }

    public String getBossPosition() {
        return bossPosition;
    }

    public void setBossPosition(String bossPosition) {
        this.bossPosition = bossPosition;
    }
}
