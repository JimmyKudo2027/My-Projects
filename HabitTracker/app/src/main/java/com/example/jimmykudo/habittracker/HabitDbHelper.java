package com.example.jimmykudo.habittracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jimmy Kudo .
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Habit.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " (" +
                    HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL," +
                    HabitContract.HabitEntry.COLUMN_HABIT_START_DATE + " TEXT," +
                    HabitContract.HabitEntry.COLUMN_HABIT_TIMES + " INTEGER NOT NULL DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME;


    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
