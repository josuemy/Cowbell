package com.ucsb.cowbell.fillblanks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ucsb.cowbell.R;
import com.ucsb.cowbell.alarm.SchedulerFragment;
import com.ucsb.cowbell.fillblanks.cards.Card;
import com.ucsb.cowbell.fillblanks.cards.CardStorage;


/**
 * Created by Josue on 4/17/16.
 */
public class CardSetterFragment extends Fragment {

    private CardStorage mCardStorage;
    private CardAddListener mCardAddListener;
    private EditText mCardTitle;
    private EditText mCardDescription;

    /**
     * Empty Constructor
     */
    public CardSetterFragment() {
    }

    /**
     * Creates a new instance of the class
     * @return a new CardSetterFragment
     */
    public static CardSetterFragment newInstance() {
        return new CardSetterFragment();
    }

    /**
     * Sets mCardAddListener to be the listener to the event when a card is being created
     * @param listener waits for a card to be created
     */
    public void setCardAddListener(CardAddListener listener) {
        mCardAddListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        View view = inflater.inflate(R.layout.fragment_card_setter, container, false);
        mCardTitle = (EditText) view.findViewById(R.id.input_card_title);
        mCardDescription = (EditText) view.findViewById(R.id.input_card_description);

        if (SchedulerFragment.whichCard == "mc_card") {
            mCardTitle.setHint("Write the Term here");
            mCardDescription.setHint("Write it's Definition here");
        }

        Button buttonOk = (Button) view.findViewById(R.id.button_ok_card_setter);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCardTitle.getText().toString().matches("") || mCardDescription.getText().toString().matches("")) {
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Card card = mCardStorage
                            .saveCard(mCardTitle.getText().toString(),
                                    mCardDescription.getText().toString());
                    String cardSaved = "Card Saved!";
                    Toast.makeText(getActivity(), cardSaved, Toast.LENGTH_LONG).show();
                    if (mCardAddListener != null) {
                        mCardAddListener.onCardAdded(card);
                    }
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });
        Button buttonCancel = (Button) view.findViewById(R.id.button_cancel_card_setter);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return view;

    }

    /**
     * Interface that will be used to add new cards to CardListFragment
     */
    public interface CardAddListener {
        void onCardAdded(Card card);
    }

}