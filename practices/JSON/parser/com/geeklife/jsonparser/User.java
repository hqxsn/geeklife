package com.geeklife.jsonparser;

import java.io.Serializable;

/**
 * Created by andysong on 14-3-7.
 */
public class User implements Serializable {

    private long userId;
    private String userName;
    private String telephone;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
