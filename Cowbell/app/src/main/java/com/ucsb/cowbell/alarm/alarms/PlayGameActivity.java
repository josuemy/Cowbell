package com.ucsb.cowbell.alarm.alarms;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucsb.cowbell.R;
import com.ucsb.cowbell.ReactGame.ReactGameActivity;
import com.ucsb.cowbell.alarm.MainActivity;
import com.ucsb.cowbell.fillblanks.FillTheBlankFragment;
import com.ucsb.cowbell.multiplechoice.MultipleChoiceQuestionFragment;

/**
 * Created by Danielle on 5/1/2016.
 */

public class PlayGameActivity extends AppCompatActivity {

    private static final String FRAGMENT_FILL_IN_THE_BLANK = "fragment_fill_in_the_blank";
    private static final String FRAGMENT_MULTIPLE_CHOICE_QUESTION = "fragment_multiply_choice_question";

    MediaPlayer mp;

    CountDownTimer playGameTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        //http://stackoverflow.com/questions/2753943/how-to-play-sound-when-button-is-clicked-in-android
        mp = MediaPlayer.create(this, R.raw.fur_elise);
        mp.start();
        mp.setLooping(true);

        this.beginTimer(this.getCurrentFocus());
    }

    @Override
    public void onStop(){
        super.onStop();
        mp.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    public void onClick(final View v) {
        playGameTimer.cancel();
        startGame();
    }

    public void startGame(){
        mp.stop();

        //http://stackoverflow.com/questions/5343544/send-a-variable-between-classes-through-the-intent
        Bundle bundle = getIntent().getExtras();
        int gameType = bundle.getInt("game_type");

        if(gameType == 0) { //fill blank
            FillTheBlankFragment fragment = FillTheBlankFragment.newInstance();
            fragment.setCancelable(false);
            fragment.show(getSupportFragmentManager(),FRAGMENT_FILL_IN_THE_BLANK);

        }
		else if(gameType == 1) { // multiple choice
            MultipleChoiceQuestionFragment fragment = MultipleChoiceQuestionFragment.newInstance();
            fragment.setCancelable(false);
            fragment.show(getSupportFragmentManager(),FRAGMENT_MULTIPLE_CHOICE_QUESTION);
		}
		else { // react game game by default
			Intent gameIntent = new Intent(this, ReactGameActivity.class);
			gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(gameIntent);
        }

    }

    /**
     * prevents the user to stop the alarm by pressing the backbutton
     */
    @Override
    public void onBackPressed(){

    }

    /**
     * Starts a timer so that if the alarm goes off, the user
     * still has to play the game after 10 seconds if they
     * do not press the button.
     * @param v
     */
    public void beginTimer(View v){
        playGameTimer=new CountDownTimer(11000,1000){ //120000 for two minutes
            TextView mTextField=(TextView)findViewById(R.id.startGameTimer);
            public void onTick(long millisUntilFinished){
                String timeRemain=""+millisUntilFinished/1000;
                mTextField.setText(timeRemain);
            }

            public void onFinish(){
                String done="done!";
                mTextField.setText(done);
                startGame();
            }
        }.start();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Intent gameIntent = new Intent(this, MainActivity.class);
        gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gameIntent);

    }
}
