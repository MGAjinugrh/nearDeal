package com.asic45.neardeal28.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    //Key Pemanggilan Fungsi
    public static final String NAMA_APLIKASI = "Near Deal";
    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String NAMA_USER = "NAMA_USER";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";


    //Define
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public SharedPrefManager(Context context) {

        sharedPreferences = context.getSharedPreferences(NAMA_APLIKASI, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserId(String key, String nilai){
        editor.putString(key,nilai);
        editor.commit();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, "");
    }


    public void setUserName(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    public void setNamaUser(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }

    public String getNamaUser() {
        return sharedPreferences.getString(NAMA_USER, "");
    }

    public void setUserPosition(String key, String nilai) {
        editor.putString(key, nilai);
        editor.commit();
    }


    public void setIsLoggedIn(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void deleteAll(){
        editor.remove(IS_LOGGED_IN);
        editor.commit();
    }

    public Boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }


}