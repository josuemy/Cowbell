package com.ucsb.cowbell.fillblanks.cards;


/**
 * Created by Josue on 4/14/16.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ucsb.cowbell.R;

import java.util.Set;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private SortedList<Card> mCardList;
    private CardStorage mCardStorage;
    private Context mContext;



    // two arg constructor
    public CardAdapter(Context context, Set<Card> cards) {
        mCardList = new SortedList<Card>(Card.class, new SortedListCallback());
        mCardList.addAll(cards);
        mCardStorage = new CardStorage(context);
        mContext = context;
    }


    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_row, parent, false);
        return new CardViewHolder(v);
    }

    // Initializes containers and sets behaviors when they are clicked
    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int position) {
        cardViewHolder.mCardTitle.setText(mCardList.get(position).title);
        cardViewHolder.mCardDescription.setText(mCardList.get(position).description);

        cardViewHolder.mDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card toBeDeleted = mCardList.get(position);
                mCardList.removeItemAt(position);
                mCardStorage.deleteCard(toBeDeleted);
                notifyDataSetChanged();
                Toast.makeText(mContext, mContext.getString(R.string.card_deleted,
                        toBeDeleted.title), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Returns the number of user created Cards
    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    // Method that adds a Card to a list of user created Cards
    public void addCard(Card card) {
        mCardList.add(card);
        notifyDataSetChanged();
    }

    // Inner Class that makes a RecycleView holder for Cards
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        private TextView mCardTitle;
        private TextView mCardDescription;
        private ImageView mDeleteImageView;
        private CheckBox mCheckBox;


        CardViewHolder(View itemView) {
            super(itemView);
            mCardTitle = (TextView) itemView.findViewById(R.id.text_card_title);
            mCardDescription = (TextView) itemView.findViewById(R.id.text_card_description);
            mDeleteImageView = (ImageView) itemView.findViewById(R.id.image_delete_card);
        }
    }

    // Inner class to sort Cards alphabetically by title
    private static class SortedListCallback extends SortedList.Callback<Card>{
        @Override
        public int compare(Card o1, Card o2) {
            return o1.compareTo(o2);
        }

        @Override
        public void onInserted(int position, int count) {
            //No op
        }

        @Override
        public void onRemoved(int position, int count) {
            //No op
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            //No op
        }

        @Override
        public void onChanged(int position, int count) {
            //No op
        }

        @Override
        public boolean areContentsTheSame(Card oldItem, Card newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areItemsTheSame(Card item1, Card item2) {
            return item1.equals(item2);
        }
    }

    // Draws a divider between Card entries
    @TargetApi(21)
    public static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;

        public DividerItemDecoration(Context context) {
            mDivider = context.getDrawable(R.drawable.divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }


}