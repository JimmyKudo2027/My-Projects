package com.example.jimmykudo.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SongList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        final Button home = (Button) findViewById(R.id.song_list_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHomeScreen();
            }
        });

        final Button nowPlay = (Button) findViewById(R.id.song_list_nowPlaying);
        nowPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPlaying();
            }
        });

    }

    public void nowPlaying() {
        Intent nowPlay = new Intent(getApplicationContext(), NowPlaying.class);
        startActivity(nowPlay);
        finish();
    }

    public void goHomeScreen() {
        Intent home = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(home);
        finish();
    }
}
