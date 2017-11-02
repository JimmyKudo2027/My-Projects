package com.example.jimmykudo.scorekeeperapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView scoreATextView;
    TextView scoreBTextView;
    int scoreA = 0;
    int scoreB = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreATextView = (TextView) findViewById(R.id.team_A_score);
        scoreBTextView = (TextView) findViewById(R.id.team_B_score);
    }

    public void calculateScore(View view){
        int id = view.getId();
        switch (id){
            case R.id.plus1_A_btn:{
                scoreA+=1;
                scoreATextView.setText(""+scoreA);
                break;
            }
            case R.id.plus2_A_btn:{
                scoreA+=2;
                scoreATextView.setText(""+scoreA);
                break;
            }
            case R.id.plus3_A_btn:{
                scoreA+=3;
                scoreATextView.setText(""+scoreA);
                break;
            }
            case R.id.plus6_A_btn:{
                scoreA+=6;
                scoreATextView.setText(""+scoreA);
                break;
            }

            case R.id.plus1_B_btn:{
                scoreB+=1;
                scoreBTextView.setText(""+scoreB);
                break;
            }
            case R.id.plus2_B_btn:{
                scoreB+=2;
                scoreBTextView.setText(""+scoreB);
                break;
            }
            case R.id.plus3_B_btn:{
                scoreB+=3;
                scoreBTextView.setText(""+scoreB);
                break;
            }
            case R.id.plus6_B_btn:{
                scoreB+=6;
                scoreBTextView.setText(""+scoreB);
                break;
            }

            case R.id.reset_btn:{
                scoreA = 0;
                scoreB = 0;
                scoreATextView.setText(""+scoreA);
                scoreBTextView.setText(""+scoreB);
            }

        }

    }
}
