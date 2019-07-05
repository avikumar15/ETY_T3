package com.example.spidertask3_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="HISTORY.db";
    public static final String TABLE_NAME="HISTORY";
    public static final String WORD="WORD";
    public static final String ETYMO="ETYMO";

    Context context;

    public HistoryDatabase(Context con) {
        super(con, DATABASE_NAME, null, 1);

        context=con;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table "+TABLE_NAME +"(WORD STRING PRIMARY KEY, ETYMO STRING)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void InsertData(String word, String etymology)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(WORD,word);
        contentValues.put(ETYMO,etymology);

        db.insert(TABLE_NAME,null,contentValues);
    }

    public Cursor getDATA()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;

    }

    public void DeleteEntireTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);
    }
}