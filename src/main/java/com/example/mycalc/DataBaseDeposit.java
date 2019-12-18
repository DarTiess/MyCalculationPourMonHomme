package com.example.mycalc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseDeposit extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "deposit01.db";
    private static final int SCHEMA = 4;
    static final String TABLE = "deposit";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_ADDMONTHLY = "addMonthly";
    public static final String COLUMN_TOTALDEPOSIT = "totalDeposit";


    public DataBaseDeposit(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE deposit (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_MONTH
                + " TEXT, " + COLUMN_ADDMONTHLY
                + " INTEGER, " + COLUMN_TOTALDEPOSIT+" INTEGER);");


       /* depositCursor=db.rawQuery("select  * from "
                + DataBaseWeek.TABLE+" ORDER BY _ID DESC LIMIT 1", null);
      depositCursor.moveToFirst();
       int depositTotal=Integer.parseInt(depositCursor.getString(3));*/

        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_MONTH + ", "+ COLUMN_ADDMONTHLY
                + ", "+ COLUMN_TOTALDEPOSIT
                + ") VALUES ('januar',6000,12500);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
