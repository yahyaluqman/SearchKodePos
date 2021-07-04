package com.example.searchkodepos.DataBase;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


//Untuk mengakses database sqlite di folder assets
public class DataBaseOpenHelper extends SQLiteAssetHelper {

    public static final String DATABASE_NAME = "kodepos.db"; //Nama database
    public static final int DATABASE_VERSION = 3;

    public DataBaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
