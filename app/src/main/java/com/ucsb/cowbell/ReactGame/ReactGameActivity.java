package com.ucsb.cowbell.ReactGame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ucsb.cowbell.R;
import com.ucsb.cowbell.ReactModel;

import java.util.ArrayList;

public class ReactGameActivity extends AppCompatActivity {

    //int counter = 0;
    static int numOfTries = 0;
    private ReactModel model = new ReactModel();

    public int getNumOfTries() {
        return numOfTries;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_react_game);
        this.updateTimer(getCurrentFocus());
        //timerBegin();
    }

    /*
    public void timerBegin() {
        new CountDownTimer(30000, 1000) {
            TextView mTextField = (TextView) findViewById(R.id.editText);
            public void onTick(long millisUntilFinished) {
                String timeRemain = "seconds remaining: " + millisUntilFinished / 1000;
                mTextField.setText(timeRemain);
            }
            public void onFinish() {
                String done = "done!";
                mTextField.setText(done);
            }
        }.start();
    } */

    public void buttonOnClick(final View b) {


        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.curry);
        images.add(R.drawable.james);
        images.add(R.drawable.harden);
        images.add(R.drawable.mcgee);
        ImageButton button = (ImageButton) b;
        int index = (int) (Math.random() * images.size());
        Integer random = images.get(index);
        button.setImageResource(random);
        //updateCounter(b,0);

        //compare drawables:
        //http://thoughtsoframy.blogspot.com/2013/08/comparing-drawables-in-android.html
        //http://stackoverflow.com/questions/29041027/android-getresources-getdrawable-deprecated-api-22

        if(button.getDrawable().getConstantState().equals(getDrawable(R.drawable.curry).getConstantState())) {
            updateCounter(b, 1);
        }
        else {
            updateCounter(b, -2);
        }
    }

    public void updateCounter(final View b, int amount) {
        //int counter = counter + amount;
        //if (counter < 0)
        //    counter = 0;

        TextView amountView = (TextView) findViewById(R.id.Amount);
        model.setCounter(amount);
        String stringAmount = "" + amount;
        if (amount > 0) {
            stringAmount = "+" + stringAmount;
        }
        amountView.setText(stringAmount);

        TextView countView = (TextView) findViewById(R.id.Counter);
        String stringCounter = "" + model.getCounter();
        countView.setText(stringCounter);
    }
    public void updateTimer(final View b) {

        new CountDownTimer(12000, 1000) {
            TextView mTextField = (TextView) findViewById(R.id.Timer);

            public void onTick(long millisUntilFinished) {
                String timeRemain = "" + millisUntilFinished / 1000;
                mTextField.setText(timeRemain);
            }

            public void onFinish() {
                String done = "done!";
                mTextField.setText(done);
                gameOver(b);
            }
        }.start();
    }

    public void gameOver(final View v) {
        //do things with a dialogue box
        numOfTries++;
        if (this.getNumOfTries() < 3) {
            new CountDownTimer(8000, 1000) {
                TextView endLoseText = (TextView) findViewById(R.id.endLose);

                public void onTick(long millisUntilFinished) {
                    String timeRemain = "You lost sucker. Game will restart in" +
                            "/n" + millisUntilFinished / 1000 + "...";
                    endLoseText.setText(timeRemain);
                    endLoseText.setBackgroundColor(Color.GREEN);
                }

                public void onFinish() {
                    //new activity
                    recreate();
                }
            }.start();
        } else {
            finish();
        }
    }
}

