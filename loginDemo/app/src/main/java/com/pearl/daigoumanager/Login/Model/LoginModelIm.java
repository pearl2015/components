package com.pearl.daigoumanager.Login.Model;


import android.os.Handler;
import android.util.Log;

import com.pearl.daigoumanager.Login.Presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Administrator on 24/06/2016.
 */
public class LoginModelIm implements LoginModel {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private JSONObject user;

    private LoginPresenter presenter;

    @Override
    public void init(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void query(final String id, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                        user = new JSONObject();
                        try {
                            user.put("username", id);
                            user.put("password", pwd);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //申明给服务端传递一个json串
                        // 创建一个OkHttpClient对象
                        OkHttpClient okHttpClient = new OkHttpClient();
                        // 创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                        String json = user.toString();
                        RequestBody requestBody = RequestBody.create(JSON, json);
                        // 创建一个请求对象
                        Request request = new Request.Builder().url("http://192.168.155.1/posttest.php").post(requestBody).build();
                        // 发送请求获取响应
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            //判断请求是否成功
                            //
                            if (response.isSuccessful()) {
                                // Log.e("Success tag", response.body().string());
                                String jsonData = response.body().string();
                                Log.e("Success tag", jsonData);
                                JSONObject Jobject = new JSONObject(jsonData);

                                int result = Jobject.getInt("is_exit");
                                Log.e("result", result + "");

                                if (result == 0) {
                                    presenter.setResult(false);

                                } else {
                                    presenter.setResult(true);
                                }
                            } else {
                                Log.e("Success tag", "failed");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
        }).start();
    }


}
