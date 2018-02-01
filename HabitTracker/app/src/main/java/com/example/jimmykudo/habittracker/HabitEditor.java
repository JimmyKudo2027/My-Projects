package com.example.jimmykudo.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jimmy Kudo .
 */

public class HabitEditor extends AppCompatActivity {

    EditText habitName;
    EditText habitDate;
    EditText habitTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        habitName = (EditText)findViewById(R.id.habitName);
        habitDate = (EditText)findViewById(R.id.habitStartDate);
        habitTimes = (EditText)findViewById(R.id.habitTimes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.habit_editor_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            addHabit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addHabit() {
        HabitDbHelper dbHelper = new HabitDbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (habitName.getText().toString().isEmpty()||habitDate.getText().toString().isEmpty()||habitTimes.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Please, Complete Your Data",Toast.LENGTH_LONG).show();
        }else{
            values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, habitName.getText().toString());
            values.put(HabitContract.HabitEntry.COLUMN_HABIT_START_DATE, habitDate.getText().toString());
            values.put(HabitContract.HabitEntry.COLUMN_HABIT_TIMES, Integer.parseInt(habitTimes.getText().toString()));
            long habitId = database.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
            Toast.makeText(getApplicationContext(),"Habit ID Is "+habitId,Toast.LENGTH_LONG).show();
            finish();
        }

    }

}
