package com.example.navbartrack.service.offline;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.navbartrack.model.UserLogin;
import com.example.navbartrack.service.DBHSingleton;


public class UserLoginServiceOff extends DBHSingleton {


    public UserLoginServiceOff(@Nullable Context context) {
        super(context);
    }

    /**
     * LOGIN
     * Check if user already has an access token that is not expired
     */
    public boolean login(UserLogin userLogin){
        Cursor c = super.getReadableDatabase().rawQuery("SELECT * FROM USER WHERE email = ?", new String[] {userLogin.getEmail()});

        if(c.getCount() != 1){
            Log.i("ERROR","Login have more than 1 result");
            return false;
        }
        else{
            if(c.moveToFirst()){
                if(c.getString(c.getColumnIndex("token")) == null){
                    Log.i("ERROR","Token null");
                    return false;
                }
                else {
                    c.getString(c.getColumnIndex("lastLogin"));
                }
            }
        }

        return false;

    }
}
