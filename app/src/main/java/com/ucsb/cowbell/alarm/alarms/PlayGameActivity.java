package com.ucsb.cowbell.alarm.alarms;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ucsb.cowbell.R;
import com.ucsb.cowbell.fillblanks.FillTheBlankFragment;

/**
 * Created by Danielle on 5/1/2016.
 */

public class PlayGameActivity extends AppCompatActivity {
    private static final String FRAGMENT_FILL_IN_THE_BLANK = "fragment_fill_in_the_blank";

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        //http://stackoverflow.com/questions/2753943/how-to-play-sound-when-button-is-clicked-in-android
        mp = MediaPlayer.create(this, R.raw.fur_elise);
        mp.start();
        mp.setLooping(true);
    }

    @Override
    public void onStop(){
        super.onStop();
        mp.stop();
    }

    public void onClick(final View v){
        mp.stop();
        FillTheBlankFragment fragment = FillTheBlankFragment.newInstance();
        fragment.setCancelable(false);
        fragment.show(getSupportFragmentManager(),FRAGMENT_FILL_IN_THE_BLANK);

    }
}




