package com.example.navbartrack.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHSingleton extends SQLiteOpenHelper {

    public DBHSingleton(@Nullable Context context) {
        super(context, "trackingmypantry.db", null, 1);

    }


    //Creaye the tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement =
                "CREATE TABLE USER (" +
                        "ID TEXT, " +
                        "USERNAME TEXT," +
                        "EMAIL TEXT," +
                        "PASSWORD TEXT,"+ //encrypted password given by the api
                        "TOKEN TEXT,"+
                        "LASTLOGIN TEXT"+ //to ensure that after a week it expires needs a new Login
                        ")";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
