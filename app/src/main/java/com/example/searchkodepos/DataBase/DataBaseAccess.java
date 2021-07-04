package com.example.searchkodepos.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Untuk membaca database
public class DataBaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DataBaseAccess instance;
    Cursor c = null;

    private DataBaseAccess(Context context){
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DataBaseAccess getInstance(Context context){
        if(instance == null){
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    //buka database
    public void open(){
        this.db = openHelper.getWritableDatabase();
    }

    //tutup database
    public void close(){
        if(db != null){
            this.db.close();
        }
    }

    //delete specific table record
    public Integer Delete(String table, String where, String value){
        return db.delete(table, where, new String[]{value});
    }

    //delete semua record di table
    public Cursor DeleteAll(String table){
        return db.rawQuery("DELETE FROM " + table, null);
    }

    //mendapatkan data dari table
    public Cursor Get(String table){
        return db.rawQuery("SELECT * FROM " + table, null);
    }

    //mendapatkan specific data dari table
    public Cursor Where(String table, String where){
        return db.rawQuery("SELECT * FROM " + table + " WHERE " + where, null);
    }

    //insert value ke dalam table
    public boolean insertFavorit(String provinsi, String kota, String kecamatan, String kelurahan, Integer kodepos){
        ContentValues contentValues = new ContentValues();
        contentValues.put("provinsi", provinsi);
        contentValues.put("kota", kota);
        contentValues.put("kecamatan", kecamatan);
        contentValues.put("kelurahan", kelurahan);
        contentValues.put("kodepos", kodepos);
        long result = db.insert("favorit", null, contentValues);
        return result != -1;
    }
}
