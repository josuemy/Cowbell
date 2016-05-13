package com.ucsb.cowbell.ReactGame;

/**
 * Created by Danielle on 4/27/2016.
 */

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;


import com.ucsb.cowbell.R;
import com.ucsb.cowbell.alarm.MainActivity;

import java.util.ArrayList;


public class ReactGameActivity extends AppCompatActivity {
    static int numOfTries;
    private ReactModel model = new ReactModel();
    ImageButton button;
    MediaPlayer mp;
    /**
     * getter for the numberOfTries
     * @return int numOfTries
     */
    public int getNumOfTries() {
        return numOfTries;
    }

    /**
     * called when the activity is launched. Uses the super method of onCreate, sets the view
     * on the screen, and starts the timer and image updates
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_react_game);
        setFinishOnTouchOutside(true);
        this.updateTimer(getCurrentFocus());
        this.updateImage(getCurrentFocus());

    }

    /**
     * creates another timer that calls a function that changes the image every few seconds.
     */
    public void updateImage(final View v){
        new CountDownTimer(60000, 900) { //60000 for one minute
            public void onTick(long millisUntilFinished) {
                generateImage(v);
            }
            public void onFinish(){
                //do nothing
            }

        }.start();

    }

    /**
     * creates an arraylist of the drawable images. sets the button to a random image
     */
    public void generateImage(final View v) {
        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.yellowlabpuppy);
        images.add(R.drawable.pug);
        images.add(R.drawable.husky);
        images.add(R.drawable.corgi);
        int index = (int) (Math.random() * images.size());
        Integer random = images.get(index);
        button =  (ImageButton) findViewById(R.id.imageButton);
        button.setImageResource(random);

    }

    /**
     * sets the textview to display a count down every second.
     * @return int numOfTries
     * @param v, final View v
     */
    public void updateTimer(final View v){
        //http://developer..com/reference/android/os/CountDownTimer.html
        new CountDownTimer(60000, 1000) { //120000 for two minutes
            TextView mTextField = (TextView) findViewById(R.id.Timer);

            public void onTick(long millisUntilFinished) {
                String timeRemain = "" + millisUntilFinished / 1000;
                mTextField.setText(timeRemain);
            }

            public void onFinish() {
                String done = "done!";
                mTextField.setText(done);
                gameOver(v);
            }
        }.start();

    }

    /**
     * checks if the current image is equal to the target image
     * if yes, add one to the score
     * if false, subtract two
     * @param v
     */
    public void buttonOnClick(final View v) {
        //compare drawables:
        //http://thoughtsoframy.blogspot.com/2013/08/comparing-drawables-in-android.html
        //http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22

        if (button.getDrawable().getConstantState().equals(getDrawable(R.drawable.yellowlabpuppy).getConstantState())) {
            updateCounter(v, 1);
        } else {
            updateCounter(v, -2);
        }
    }

    /**
     * getter for the numberOfTries
     * @param v the view
     * @param amount the amount you are adding or subtracting from the count
     * @return int numOfTries
     */
    public void updateCounter(final View v, int amount){

        TextView amountView = (TextView) findViewById(R.id.Amount);
        model.setCounter(amount);
        if(model.getCounter() == 5) {
            numOfTries = 3;
            gameOver(v);
        }
        String stringAmount = "" + amount;
        if(amount > 0) { stringAmount = "+" + stringAmount; }
        amountView.setText(stringAmount);

        TextView countView = (TextView) findViewById(R.id.Counter);
        String stringCounter = "" + model.getCounter();
        countView.setText(stringCounter);
    }




    /**
     * when called, either restarts the game when you lose,
     * or if you win, it just quits the activity
     * @param v, the view of the screen
     */
    public void gameOver(final View v){
        //do things with a dialogue box.
        mp = MediaPlayer.create(this, R.raw.fur_elise);
        numOfTries++;
        if(this.getNumOfTries() < 3) {

            //dialogue box then restart game
            new CountDownTimer(5000, 1000) { //120000 for two minutes
                TextView endLoseText = (TextView) findViewById(R.id.endLose);

                public void onTick(long millisUntilFinished) {
                    mp.start();
                    mp.setLooping(true);
                    String timeRemain = "You lost! Game will restart in" +
                            "\n" + millisUntilFinished / 1000 + "...";
                    endLoseText.setText(timeRemain);
                    endLoseText.setBackgroundColor(Color.RED);
                }

                public void onFinish() {
                    //do a new activity.
                    mp.stop();
                    recreate();
                }
            }.start();

        }
        else{
            //exit activity and return to main screen.
            numOfTries = 0;
            Intent gameIntent = new Intent(this, MainActivity.class);
            gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(gameIntent);
        }
    }

    /**
     * prevents user to go back and end the game without finishing
     */
    @Override
    public void onBackPressed(){

    }
}
