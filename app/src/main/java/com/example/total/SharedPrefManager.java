package com.example.total;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    //Declaring components
    private static SharedPrefManager minstance;

   // private ImageLoader imageLoader;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String Key_USER_ID="userid";
    private static final String KEY_USER_EMAIL="usermail";
    private static final String KEY_USER_NO="consumer_no";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (minstance == null) {
            minstance = new SharedPrefManager(context);
        }
        return minstance;
    }
    public boolean userLogin(String id, String consumer_no  ,String email){//
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString(Key_USER_ID,id);
        editor.putString(KEY_USER_NO,consumer_no);
        editor.putString(KEY_USER_EMAIL,email);

        editor.apply();;
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getLpgId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key_USER_ID,null);
    }
    public String getUserEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_USER_EMAIL,null);
    }
    public String getConsumerNo(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(KEY_USER_NO,null);
    }

}

