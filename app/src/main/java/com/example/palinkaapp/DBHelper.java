package com.example.palinkaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "palinkaapp.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "palinka";
    private static final String COL_ID = "id";
    private static final String COL_FOZO = "fozo";
    private static final String COL_GYUMOLCS = "gyumolcs";
    private static final String COL_ALKOHOL = "alkohol";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABLE_NAME +" (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FOZO + " TEXT NOT NULL, " +
                COL_GYUMOLCS + " TEXT NOT NULL, " +
                COL_ALKOHOL + " INTEGER NOT NULL " +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean rogzites(String fozo, String gyumolcs, int alkhol){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FOZO, fozo);
        values.put(COL_GYUMOLCS, gyumolcs);
        values.put(COL_ALKOHOL, alkhol);
        return db.insert(TABLE_NAME, null, values) != -1;
    }

    public Cursor listaz() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, new String[]{COL_ID,COL_FOZO,COL_GYUMOLCS,COL_ALKOHOL},
                null,null,null,null,null, null);
    }

    public Cursor palinkaKeres(String fozo, String gyumolcs){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT alkohol FROM palinka WHERE fozo = ? AND gyumolcs = ? Limit 0,1", new String[]{fozo, gyumolcs});
                /*db.query(TABLE_NAME, new String[]{COL_ALKOHOL},
                null,null,null,null,null, null);*/
    }
}
