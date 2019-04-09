package com.ycjx.utils;

/**
 * @author:yuxj
 * @descriptio
 * @create:2019-04-08 22:59
 */
public class Session {

    private String userId;

    private String userName;

    public Session(String userId, String username) {
        this.userId = userId;
        this.userName = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
