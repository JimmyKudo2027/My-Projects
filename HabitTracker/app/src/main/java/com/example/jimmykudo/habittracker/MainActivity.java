package com.example.jimmykudo.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jimmy Kudo .
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addHabit);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HabitEditor.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dummy) {
            insertDummy();
            displayDatabaseInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = new String[]{
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_START_DATE,
                HabitContract.HabitEntry.COLUMN_HABIT_TIMES
        };
        Cursor cursor = db.query(HabitContract.HabitEntry.TABLE_NAME,projection,null,null,null,null,null,null);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // habits table in the database).
            TextView displayView = (TextView) findViewById(R.id.tableOfHabits);
            displayView.setText("\n\n\n\n");
            displayView.append("Number of Habits : " + cursor.getCount());
            displayView.append("\n\n"+HabitContract.HabitEntry._ID +" - "+
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME+" - "+
                    HabitContract.HabitEntry.COLUMN_HABIT_START_DATE+" - "+
                    HabitContract.HabitEntry.COLUMN_HABIT_TIMES+"\n");
            while (cursor.moveToNext()){
                displayView.append("\n"+cursor.getInt(cursor.getColumnIndex(HabitContract.HabitEntry._ID)) +" - "+
                        cursor.getString(cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME))+" - "+
                        cursor.getString(cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_START_DATE))+" - "+
                        cursor.getInt(cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_TIMES)));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertDummy() {
        HabitDbHelper dbHelper = new HabitDbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, "Watching Anime");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_START_DATE, "11-11-2017");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TIMES, 3);
        long petId = database.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        Toast.makeText(getApplicationContext(), "Habit ID Is " + petId, Toast.LENGTH_LONG).show();
    }
}
