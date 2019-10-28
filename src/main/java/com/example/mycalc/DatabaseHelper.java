package com.example.mycalc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper  extends SQLiteOpenHelper {
  //  private static String DB_PATH="assets";
    private static final String DATABASE_NAME = "calculation01.db";
    private static final int SCHEMA = 2;
    static final String TABLE = "calc";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MSNAME = "msName";
    public static final String COLUMN_MONEY = "money";
    public static final String COLUMN_ENDMONEY = "endmoney";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE calc (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_MSNAME
                + " TEXT, " + COLUMN_MONEY
                + " INTEGER, " + COLUMN_ENDMONEY + " INTEGER);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_MSNAME + ", "+ COLUMN_MONEY
                + ", " + COLUMN_ENDMONEY  + ") VALUES ('январь', 25900 , 20);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
