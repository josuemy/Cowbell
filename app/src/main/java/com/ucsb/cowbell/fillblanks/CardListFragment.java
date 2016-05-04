package com.ucsb.cowbell.fillblanks;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucsb.cowbell.R;

import com.ucsb.cowbell.fillblanks.cards.Card;
import com.ucsb.cowbell.fillblanks.cards.CardAdapter;
import com.ucsb.cowbell.fillblanks.cards.CardStorage;

/**
 * Created by Josue on 4/17/16.
 */
public class CardListFragment extends Fragment  {

    private  static final String FRAGMENT_CARD_SETTER_TAG = "fragment_card_setter";
    private static final String FRAGMENT_FILL_THE_BLANK_TAG ="fragment_fill_the_blank";

    private TextView mTextViewIntroMessage;
    private CardAdapter mCardAdapter;

    public static CardListFragment newInstance() {
        CardListFragment fragment = new CardListFragment();
        return fragment;
    }

    public CardListFragment() {/*empty constructor*/}


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstance){
        super.onActivityCreated(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_list, container, false);
    }


    @Override
    public void onViewCreated(final View rootView, Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        FloatingActionButton fabAdd = (FloatingActionButton) rootView.findViewById(R.id.fab_add_card);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardSetterFragment fragment = CardSetterFragment.newInstance();
                fragment.setCardAddListener(new CardAddListenerImpl());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment, FRAGMENT_CARD_SETTER_TAG);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        mTextViewIntroMessage = (TextView) rootView.findViewById(R.id.text_intro_message);
        Activity activity = getActivity();
        CardStorage cardStorage = new CardStorage(activity);
        mCardAdapter = new CardAdapter(activity, cardStorage.getCards());
        if (mCardAdapter.getItemCount() == 0) {
            mTextViewIntroMessage.setVisibility(View.VISIBLE);
        }


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(mCardAdapter);
        recyclerView.addItemDecoration(new CardAdapter.DividerItemDecoration(activity));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    private class CardAddListenerImpl implements CardSetterFragment.CardAddListener {

        public void onCardAdded(Card card){
            mCardAdapter.addCard(card);
            mTextViewIntroMessage.setVisibility(View.GONE);
        }



    }



}