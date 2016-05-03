package com.ucsb.cowbell.fillblanks;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ucsb.cowbell.R;
import com.ucsb.cowbell.fillblanks.cards.Card;
import com.ucsb.cowbell.fillblanks.cards.CardAdapter;
import com.ucsb.cowbell.fillblanks.cards.CardStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * Created by Josue on 4/19/16.
 */
public class FillTheBlankFragment extends DialogFragment {

    private TextView mDialogTitle;
    private EditText mInputAnswer;
    private TextView mCardDescription;
    private CardStorage mCardStorage;
    private CardAdapter mCardAdapter;
    private String mActualAnswer;
    private int numOfTries;



    public FillTheBlankFragment() {

    }

    public static FillTheBlankFragment newInstance() {
        return new FillTheBlankFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCardStorage = new CardStorage(getActivity());
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_the_blank, container, false);
        this.updateTimer(view);

        mDialogTitle = (TextView) view.findViewById(R.id.title_fill_the_blank);
        mCardDescription = (TextView) view.findViewById(R.id.description_to_complete);
        mCardDescription.setText(Card.descriptionSelected);
        changeDescription();

        mInputAnswer = (EditText) view.findViewById(R.id.answer_input);

        Button buttonOk = (Button) view.findViewById(R.id.button_ok_fill_the_blank);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mInputAnswer.getText().toString().equalsIgnoreCase(mActualAnswer)){
                    dismiss();
                }
                else {
                    Toast.makeText(getContext(),"Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void changeDescription(){

        List<String> myList = new ArrayList<String>(Arrays.asList(mCardDescription.getText().toString().split("\\s*(\\s|,|\\s)\\s*")));
        int idx = new Random().nextInt(myList.size());
        if(approvedWord(myList.get(idx))) {
            mActualAnswer = (myList.get(idx));
            myList.set(idx, "_____");
            String result = TextUtils.join(" ",myList) + "  A:" + mActualAnswer;
            mCardDescription.setText(result);
        }else{changeDescription();}
    }

    private boolean approvedWord(String toVerify){
        String[] wordsToOmit = {"the","a","is","are","was","were","for","not","or","in","I","he","she","you","we","to","on","am","[a-zA-Z]+?","[a-zA-Z]+,"};
        List<String> omitList = Arrays.asList(wordsToOmit);
        return (!(omitList.contains(toVerify)));
    }

    public void updateTimer(final View v) {

        new CountDownTimer(12000, 1000) {
            TextView mTextField = (TextView) v.findViewById(R.id.timer);

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


    public void gameOver(final View v) {
        //do things with a dialogue box
        numOfTries++;
        if (numOfTries < 3) {
            new CountDownTimer(8000, 1000) {
                TextView endLoseText = (TextView) v.findViewById(R.id.endLose);

                public void onTick(long millisUntilFinished) {
                    String timeRemain = "You lost sucker. Game will restart in "
                            + millisUntilFinished / 1000 + "...";
                    endLoseText.setText(timeRemain);
                    endLoseText.setBackgroundColor(Color.GREEN);
                }

                public void onFinish() {
                    //new activity
                    String empty = "";
                    endLoseText.setText(empty);
                    updateTimer(v);
                }
            }.start();
        } else {
            numOfTries = 0;
            dismiss();
        }
    }

}