package com.example.jimmykudo.habittracker;

import android.provider.BaseColumns;

/**
 * Created by Jimmy Kudo .
 */

public final class HabitContract {

    public static abstract class HabitEntry implements BaseColumns{
        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_START_DATE = "start";
        public static final String COLUMN_HABIT_TIMES = "times";

    }

}
