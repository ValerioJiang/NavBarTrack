package com.example.navbartrack.service.offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


import com.example.navbartrack.model.UserRegistration;
import com.example.navbartrack.service.DBHSingleton;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationServiceOff extends DBHSingleton {

    public UserRegistrationServiceOff(@Nullable Context context) {
        super(context);
    }

    /**
     * LIST
     */
    public List<UserRegistration> listCustomer(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from USER", null);
        List<UserRegistration> results = new ArrayList<UserRegistration>();
        try {
            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    String username = c.getString(1).replaceAll("\"","");
                    String email = c.getString(2).replaceAll("\"","");
                    String password = c.getString(3).replaceAll("\"","");

                    UserRegistration userRegistrationTemp = new UserRegistration(id,username, email, password);
                    results.add(userRegistrationTemp);
                }while(c.moveToNext());
            }
        }
        catch (Error e){
            System.out.println("Errore nel listCustomer() classe DataBaseHelper");
            System.out.println(e);
        }
        c.close();
        db.close();
        return results;
    }

    /**
     * CREATE
     */
    public boolean userRegistration(UserRegistration newUser) {
        ContentValues cv = new ContentValues();

        cv.put("username", newUser.getUsername());
        cv.put("email", newUser.getEmail());
        cv.put("password", newUser.getPassword());

        long insert = super.getWritableDatabase().insert("USER",null, cv);

        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

}
