package com.example.mycalc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseWeek  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weeks.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "week";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ROAD = "road";
    public static final String COLUMN_LUNCH = "lunch";
    public static final String COLUMN_CIGARRE = "cigarre";
    public static final String COLUMN_RESULT_FOR_WEEK = "resultForWeek";
    public DataBaseWeek(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE week (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_ROAD
                + " INTEGER, " + COLUMN_LUNCH
                + " INTEGER, " + COLUMN_CIGARRE+" INTEGER, " + COLUMN_RESULT_FOR_WEEK + " INTEGER);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_ROAD + ", "+ COLUMN_LUNCH
                + ", "+ COLUMN_CIGARRE
                + ", " + COLUMN_RESULT_FOR_WEEK  + ") VALUES (7000,6000,2500,10700);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
