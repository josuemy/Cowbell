package com.ucsb.cowbell.ReactGame;

/**
 * Created by georgelieu14 on 4/29/16.
 */

public class ReactModel {
    private int counter;
    private int timeRemain;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int amount) {
        counter = counter + amount;
        if(counter < 0)
            counter = 0;
        if (counter > 5)
            counter = 5;
    }


}