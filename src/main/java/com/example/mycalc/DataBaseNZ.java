package com.example.mycalc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseNZ extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nz.db";
    private static final int SCHEMA = 5;
    static final String TABLE = "nz";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_ADDNZMONTHLY = "addNZMonthly";
    public static final String COLUMN_TOTALNZ = "totalNZ";

    public DataBaseNZ(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE nz (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_MONTH
                + " TEXT, " + COLUMN_ADDNZMONTHLY
                + " INTEGER, " + COLUMN_TOTALNZ+" INTEGER);");


        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_MONTH + ", "+ COLUMN_ADDNZMONTHLY
                + ", "+ COLUMN_TOTALNZ
                + ") VALUES ('januar',3000,14500);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
