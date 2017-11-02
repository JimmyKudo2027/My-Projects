package com.example.jimmykudo.eduquizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup question_1;
    RadioGroup question_2;
    Boolean question_3_A = false;
    Boolean question_3_B_ans = false;
    Boolean question_3_C = false;
    Boolean question_3_D_ans = false;
    EditText question_4;
    TextView grade;
    int degree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question_1 = (RadioGroup) findViewById(R.id.Q_1);
        question_2 = (RadioGroup) findViewById(R.id.Q_2);
        question_4 = (EditText) findViewById(R.id.Q_4_Answer);
        grade = (TextView) findViewById(R.id.degree);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.Q_3_A: {
                if (checked) {
                    question_3_A = true;
                }else {
                    question_3_A = false;
                }
                break;
            }
            case R.id.Q_3_B_Answer: {
                if (checked) {
                    question_3_B_ans = true;
                } else {
                    question_3_B_ans = false;
                }
                break;
            }
            case R.id.Q_3_C: {
                if (checked) {
                    question_3_C = true;
                }else {
                    question_3_C = false;
                }
                break;
            }
            case R.id.Q_3_D_Answer: {
                if (checked) {
                    question_3_D_ans = true;
                } else {
                    question_3_D_ans = false;
                }
                break;
            }
        }
    }

    public void calcGrade(View view) {
        if (question_1.getCheckedRadioButtonId() == R.id.Q_1_B_Answer){
            degree+=25;
        }
        if (question_2.getCheckedRadioButtonId() == R.id.Q_2_C_Answer){
            degree+=25;
        }
        if (!question_3_A && !question_3_C){
            if (question_3_B_ans){
                degree+=12;
            }
            if(question_3_D_ans){
                degree+=13;
            }
        }
        if (!question_4.getText().toString().isEmpty()){
            if (question_4.getText().toString().equalsIgnoreCase(getString(R.string.Q_4_Answer))){
                degree+=25;
            }
        }
        grade.setText(getString(R.string.degree).replace("No Grade Yet",""+degree));
        Toast.makeText(this,getString(R.string.degree).replace("No Grade Yet",""+degree),Toast.LENGTH_LONG).show();
        degree = 0;
    }
}
