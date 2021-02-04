package com.example.app2_co;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelperImpl extends AdminSQLiteOpenHelper {
    public AdminSQLiteOpenHelperImpl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL("create table articulos(codigo int primary key, description text, precio real)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

