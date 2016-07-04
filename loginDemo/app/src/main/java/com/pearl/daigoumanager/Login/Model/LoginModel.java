package com.pearl.daigoumanager.Login.Model;

import com.pearl.daigoumanager.Login.Presenter.LoginPresenter;

/**
 * Created by Administrator on 24/06/2016.
 */
public interface LoginModel {

    public void query(String id, String pwd);
    public void init(LoginPresenter presenter);
}
