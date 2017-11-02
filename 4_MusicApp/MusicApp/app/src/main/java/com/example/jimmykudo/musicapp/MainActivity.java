package com.example.jimmykudo.musicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button songsList = (Button) findViewById(R.id.main_song_list);
        songsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songList();
            }
        });

        final Button nowPlay = (Button) findViewById(R.id.main_now_playing);
        nowPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nowPlaying();
            }
        });

        final Button payment = (Button) findViewById(R.id.main_payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentAct();
            }
        });

    }

    public void songList() {
        Intent songList = new Intent(getApplicationContext(), SongList.class);
        startActivity(songList);
        finish();
    }

    public void nowPlaying() {
        Intent nowPlay = new Intent(getApplicationContext(), NowPlaying.class);
        startActivity(nowPlay);
        finish();
    }

    public void paymentAct() {
        Intent payment = new Intent(getApplicationContext(), PaymentActivity.class);
        startActivity(payment);
        finish();
    }
}
