package com.example.navbartrack.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.navbartrack.model.UserLogin;


public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";
    String ACCESS_TOKEN = null;

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(UserLogin user){
        //save session of user whenever user is logged in
        this.ACCESS_TOKEN = user.getToken();

        editor.putString(SESSION_KEY, ACCESS_TOKEN).commit();
    }

    public String getSession(){
        //return user id whose session is saved
        return sharedPreferences.getString(SESSION_KEY, ACCESS_TOKEN);
    }

    //To remove the session, just check the null value
    public void removeSession(){
        editor.putString(SESSION_KEY, null).commit();
    }
}
