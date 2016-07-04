package com.pearl.daigoumanager.Tools;

import android.content.SharedPreferences;

import com.pearl.daigoumanager.Beans.User;

/**
 * Created by Administrator on 22/06/2016.
 */
public class Utils {

    // SharedPreferences sp;

    public static User readfromSharedPreferences(SharedPreferences sp){
        SharedPreferences spw = sp;
        //SharedPreferences.Editor editor = sp.edit();
        String id = sp.getString("id",null);
        String psw = sp.getString("psw", null);
        User user = new User(id,psw);
        return user;
    }
    public static void writetoSharedPreferences(SharedPreferences sp,String id, String psw){
        SharedPreferences spw = sp;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", id);
        editor.putString("psw", psw);
        editor.commit();
    }

    public static void writeCheckStatetoHSaredPreferences(SharedPreferences sp, boolean is_check){
        SharedPreferences spw = sp;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("is_check", is_check);
        editor.commit();
    }

}
