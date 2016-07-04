package com.pearl.daigoumanager.Login.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pearl.daigoumanager.Beans.User;
import com.pearl.daigoumanager.Login.Presenter.LoginPresenter;
import com.pearl.daigoumanager.Login.Presenter.LoginPresenterIm;
import com.pearl.daigoumanager.MainActivity;
import com.pearl.daigoumanager.R;
import com.pearl.daigoumanager.Tools.Utils;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private EditText mIdEditText;
    private EditText mPwdEditText;
    private Button mLoginBtn;
    private String mIdString;
    private String mPwdString;
    private CheckBox mCheckPwd;


    private ArrayList<User> userlist;
    private SharedPreferences sp;

    LoginPresenter presenter;

    //弹出正在登录对话框
    ProgressDialog mLoginingDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setListener();

        //此处应该获得已经保存的用户密码
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        mIdEditText.setText(sp.getString("id", ""));
        mPwdEditText.setText(sp.getString("psw", ""));
        mCheckPwd.setChecked(sp.getBoolean("is_check",false));

    }

    private void initView() {
        mIdEditText = (EditText) findViewById(R.id.txtUserName);
        mPwdEditText = (EditText) findViewById(R.id.txtPassword);
        mLoginBtn = (Button) findViewById(R.id.btnLogin);
        mCheckPwd = (CheckBox) findViewById(R.id.cbPass);

        //保存用户名密码
        mCheckPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Utils.writetoSharedPreferences(sp, mIdString, mPwdString);
                }
                Utils.writeCheckStatetoHSaredPreferences(sp, isChecked);

            }

        });
    }

    private void setListener() {
        mIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mIdString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPwdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPwdString = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                // 启动登录
                mIdString = mIdEditText.getText().toString();
                mPwdString = mPwdEditText.getText().toString();

                if (mIdString == null || mIdString.equals("")) { // 账号为空时
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT)
                            .show();
                } else if (mPwdString == null || mPwdString.equals("")) {// 密码为空时
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
                            .show();
                } else {// 账号和密码都不为空时

                    showDialog();
                    presenter = new LoginPresenterIm();
                    presenter.checkUser(mIdString, mPwdString, LoginActivity.this);
                }
                break;
            default:
                break;
        }

    }

    public void showDialog() {
        mLoginingDlg = ProgressDialog.show(LoginActivity.this, "请等待", "正在登录...", true);
    }

    @Override
    public void setResult(boolean is_exit) {
        if (is_exit)  //存在
        {
            this.mLoginingDlg.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
                }
            });

        } else {//不存在
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "用户不存在或密码错误", Toast.LENGTH_SHORT).show();
                }
            });
            this.mLoginingDlg.dismiss();
        }
    }
}
