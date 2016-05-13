package com.ucsb.cowbell.multiplechoice;

import android.os.Bundle;
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

        CardStorage mCardStorage = new CardStorage(getActivity());
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

        ArrayList<Button> buttonArrayList = new ArrayList<Button>();
        buttonArrayList.add(buttonA);
        buttonArrayList.add(buttonB);
        buttonArrayList.add(buttonC);
        buttonArrayList.add(buttonD);

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
                numberCorrect = 0;
            } else {
                dismiss();
                MultipleChoiceQuestionFragment fragment = MultipleChoiceQuestionFragment.newInstance();
                fragment.setCancelable(false);
                fragment.show(getFragmentManager(), "fragment_multiple_choice_question");
            }
        } else
            Toast.makeText(getContext(), "Try Again", Toast.LENGTH_SHORT).show();
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


}
