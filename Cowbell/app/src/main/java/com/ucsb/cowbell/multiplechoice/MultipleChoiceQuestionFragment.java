package com.ucsb.cowbell.multiplechoice;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.ucsb.cowbell.R;
import com.ucsb.cowbell.fillblanks.cards.Card;
import com.ucsb.cowbell.fillblanks.cards.CardStorage;


public class MultipleChoiceQuestionFragment extends DialogFragment implements View.OnClickListener {
    private String cardTitle;
    private static int numberCorrect;
    private static int numberWrong;
    private static int numberTries;
    TextView mTextField;
    CountDownTimer blankTimer;
    MediaPlayer mp;
    ArrayList<Button> buttonArrayList;

    public MultipleChoiceQuestionFragment() {}

    public static MultipleChoiceQuestionFragment newInstance() {
        return new MultipleChoiceQuestionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /*
    * Initializes the containers and get all user created cards
    * Randomizes question and answers
    * */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);
        mTextField = (TextView) view.findViewById(R.id.mc_timer);

        CardStorage mCardStorage = new CardStorage(getActivity(),"mc_card");
        Set<Card> mSet = mCardStorage.getCards();
        List<Card> mList = new ArrayList<Card>(mSet);

        Card card = mList.get(new Random().nextInt(mList.size()));
        cardTitle = card.title;
        String cardDescription = card.description;

        TextView definitionView = (TextView) view.findViewById(R.id.definitionView);
        Button buttonA = (Button) view.findViewById(R.id.choiceA);
        Button buttonB = (Button) view.findViewById(R.id.choiceB);
        Button buttonC = (Button) view.findViewById(R.id.choiceC);
        Button buttonD = (Button) view.findViewById(R.id.choiceD);

        buttonArrayList = new ArrayList<Button>();
        buttonArrayList.add(buttonA);
        buttonArrayList.add(buttonB);
        buttonArrayList.add(buttonC);
        buttonArrayList.add(buttonD);

        this.updateTimer(view);

        definitionView.setText(cardDescription);
        buttonArrayList.get(new Random().nextInt(4)).setText(cardTitle);
        ArrayList<String> usedNames = new ArrayList<String>();
        usedNames.add(cardTitle);

        for (Button b : buttonArrayList) {
            b.setOnClickListener(this);
            String bName;
            if (b.getText().toString().equals("")) {
                bName = nameHelper(mList, usedNames);
                usedNames.add(bName);
                b.setText(bName);
            }
        }


        return view;
    }


    /*
    * ActionListener that checks if the button with the correct answer was pressed
    * If the question was answered correctly, continue
    * If the question was answered incorrectly, keep trying
    * */
    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        if (b.getText().toString().equals(cardTitle)) {
            numberCorrect++;
            Toast.makeText(getContext(), "Correct!", Toast.LENGTH_SHORT).show();
            if (numberCorrect >= 3) {
                dismiss();
                getActivity().finish();
                numberCorrect = 0;
                numberTries = 0;
            } else {
                dismiss();
                MultipleChoiceQuestionFragment fragment = MultipleChoiceQuestionFragment.newInstance();
                fragment.setCancelable(false);
                fragment.show(getFragmentManager(), "fragment_multiple_choice_question");
            }
        } else {
            Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
            numberWrong++;
        }
        if (numberWrong >= 2)
            gameOver(v);
    }


    /*
    * Helper function to randomize incorrect answers
    * Ensures there are no duplicate answers
    * */
    public String nameHelper(List<Card> in, ArrayList<String> uN) {
        String s;

        int ran = new Random().nextInt(in.size());
        s = in.get(ran).title;
        if (uN.contains(s))
            s = nameHelper(in, uN);

        return s;
    }

    public void updateTimer(final View v) {
        blankTimer = new CountDownTimer(15000, 1000) {
            public void onTick(long millisUntilFinished) {
                String timeRemain = "" + millisUntilFinished / 1000;
                mTextField.setText(timeRemain);
            }

            public void onFinish() {
                if(getFragmentManager() != null) {
                    gameOver(v);
                }
            }
        }.start();

    }

    public void gameOver(final View v){
        numberTries++;
        numberWrong = 0;
        mp = MediaPlayer.create(getActivity(), R.raw.fur_elise);
        blankTimer.cancel();
        new CountDownTimer(7000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("You ran out of time! The game will resume in " + millisUntilFinished / 1000);
                mp.start();
                mp.setLooping(true);
                for (Button b : buttonArrayList) {
                    if (b.getText().toString().equals(cardTitle)) {
                        b.setTextColor(Color.WHITE);
                        b.setBackgroundColor(Color.GREEN);
                    }
                    b.setEnabled(false);
                }
            }

            public void onFinish() {
                dismiss();
                if (numberTries < 3) {
                    MultipleChoiceQuestionFragment fragment = MultipleChoiceQuestionFragment.newInstance();
                    fragment.setCancelable(false);
                    fragment.show(getFragmentManager(), "fragment_multiple_choice_question");
                    mp.stop();
                    updateTimer(v);
                } else {
                    mp.stop();
                    getActivity().finish();
                    numberCorrect = 0;
                    numberTries = 0;
                }
            }
        }.start();

    }

}
