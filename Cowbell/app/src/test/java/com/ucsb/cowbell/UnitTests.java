package com.ucsb.cowbell;

import android.app.Fragment;

import com.ucsb.cowbell.ReactGame.ReactModel;
import com.ucsb.cowbell.fillblanks.FillTheBlankFragment;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UnitTests {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void take_word_isCorrect() throws  Exception{
        String[] wordsToOmit = {"the","a","is","are","and","was","were","for","not","or","in","I","it","he",
                "she","you","we","they","no","to","on","am","of"};

        for(int i= 0;i<wordsToOmit.length;i++) {
            assertEquals(false, FillTheBlankFragment.newInstance().approvedWord(wordsToOmit[i]));
        }
    }

    @Test
    public void test_getCounter() throws Exception {

        ReactModel model = new ReactModel();
        int expected;
        int actual = model.getCounter();


        model.setCounter(-5);
        expected = 0;
        assertEquals(expected, actual);

        model.setCounter(3);
        actual = model.getCounter();
        expected = 3;
        assertEquals(expected, actual);

        model.setCounter(5);
        actual = model.getCounter();
        expected = 5;
        assertEquals(expected, actual);

        model.setCounter(3);
        actual = model.getCounter();
        expected = 5;
        assertEquals(expected, actual);
    }

}