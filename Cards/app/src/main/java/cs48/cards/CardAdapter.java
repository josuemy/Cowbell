package cs48.cards;

/**
 * Created by Josue on 4/14/16.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Set;

import cs48.FillTheBlankFragment;
import cs48.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private SortedList<Card> mCardList;
    private CardStorage mCardStorage;
    private Context mContext;




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

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, final int position) {


        cardViewHolder.mCardTitle.setText(mCardList.get(position).title);
        cardViewHolder.mCardDescription.setText(mCardList.get(position).description);

        cardViewHolder.mCheckBox.setChecked(mCardList.get(position).isSelected());
        cardViewHolder.mCheckBox.setTag(mCardList.get(position));



        cardViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               CheckBox cb = (CheckBox) view;
                Card card = (Card) cb.getTag();
                card.setSelected(cb.isChecked());
                card.changeDescriptionSelected(card.description);
                if (cb.isChecked()) {
                    Toast.makeText(mContext, "Only the last card selected will count", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Select another card", Toast.LENGTH_SHORT).show();
                }
            }

        });


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



    @Override
    public int getItemCount() {
        return mCardList.size();
    }


    public void addCard(Card card) {
        mCardList.add(card);
        notifyDataSetChanged();
    }


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
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

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







