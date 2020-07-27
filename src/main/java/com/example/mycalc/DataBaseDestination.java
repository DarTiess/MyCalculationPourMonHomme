package com.example.mycalc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseDestination extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "destination.db";
    private static final int SCHEMA = 3;
    static final String TABLE = "destination";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DEFINITION = "definition";
    public static final String COLUMN_VALUE = "value";

    public DataBaseDestination(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE destination (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_DEFINITION
                + " TEXT, " + COLUMN_VALUE
                + " INTEGER);");

        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_DEFINITION + ", "+ COLUMN_VALUE
                 + ") VALUES ('Отпуск', 40000);");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_DEFINITION + ", "+ COLUMN_VALUE
                + ") VALUES ('Квартира', 40000);");
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_DEFINITION + ", "+ COLUMN_VALUE
                + ") VALUES ('Машина', 40000);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}
