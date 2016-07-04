package com.pearl.daigoumanager.Login.Presenter;


import com.pearl.daigoumanager.Login.View.LoginView;

/**
 * Created by Administrator on 27/06/2016.
 */
public interface LoginPresenter {
    public void checkUser(String id, String pwd, LoginView loginView);
    public void setResult(boolean result);
}
