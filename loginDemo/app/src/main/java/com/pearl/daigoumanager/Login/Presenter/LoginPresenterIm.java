package com.pearl.daigoumanager.Login.Presenter;

import android.os.Handler;
import android.util.Log;

import com.pearl.daigoumanager.Login.Model.LoginModel;
import com.pearl.daigoumanager.Login.Model.LoginModelIm;
import com.pearl.daigoumanager.Login.View.LoginView;

import org.junit.internal.runners.statements.RunAfters;

/**
 * Created by Administrator on 27/06/2016.
 */
public class LoginPresenterIm implements LoginPresenter {

    LoginView loginView;
    LoginModel loginModel;

    public boolean is_exit;

    @Override
    public void checkUser(String id, String pwd, LoginView loginView) {

         this.loginView =  loginView;
         loginModel = new LoginModelIm();

        loginModel.init(this);
        loginModel.query(id, pwd);

    }
    @Override
    public void setResult(boolean result){
        this.is_exit = result;
        new Thread(new Runnable() {
            @Override
            public void run() {
                loginView.setResult(is_exit);
            }
        }).start();
    }
}
