package com.xuhao.myapp.Bean;

public class UserInfoBean {
    private long userid;
    private String userpassword;
    private String username;
    private String usersex;
    private int userage;
    private String userhead;
    private int usertypeid;

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "userid='" + userid + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", username=" + username +
                ", usersex=" + usersex +
                ", userage=" + userage +
                ", userhead=" + userhead +
                ", usertypeid=" + usertypeid + '}';
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public int getUserage() {
        return userage;
    }

    public void setUserage(int userage) {
        this.userage = userage;
    }

    public String getUserhead() {
        return userhead;
    }

    public void setUserhead(String userhead) {
        this.userhead = userhead;
    }

    public int getUsertypeid() {
        return usertypeid;
    }

    public void setUsertypeid(int usertypeid) {
        this.usertypeid = usertypeid;
    }

    public UserInfoBean(long userid) {
        this.userid = userid;
    }


}
