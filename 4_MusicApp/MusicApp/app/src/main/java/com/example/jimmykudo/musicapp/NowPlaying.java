package com.example.jimmykudo.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NowPlaying extends AppCompatActivity {

    Boolean play = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        final Button home = (Button) findViewById(R.id.now_play_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomeScreen();
            }
        });

        final Button songsList = (Button) findViewById(R.id.now_play_songList);
        songsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songList();
            }
        });

        final Button play_pause = (Button) findViewById(R.id.now_play_playPause);
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPause();
            }
        });
    }

    public void goHomeScreen() {
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);
        finish();
    }

    public void songList() {
        Intent songList = new Intent(getApplicationContext(), SongList.class);
        startActivity(songList);
        finish();
    }

    public void playPause() {
        if(play){
            Toast.makeText(getApplicationContext(), "Play", Toast.LENGTH_LONG).show();
            play = false;
        }else {
            Toast.makeText(getApplicationContext(), "Pause", Toast.LENGTH_LONG).show();
            play = true;
        }
    }
}
