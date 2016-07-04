package com.pearl.daigoumanager.Beans;

/**
 * Created by Administrator on 22/06/2016.
 */
public class User {
    private String mId;
    private String mPwd;

    public User(){}
    public User(String mId, String mPwd) {
        this.mId = mId;
        this.mPwd = mPwd;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmPwd() {
        return mPwd;
    }

    public void setmPwd(String mPwd) {
        this.mPwd = mPwd;
    }
}
