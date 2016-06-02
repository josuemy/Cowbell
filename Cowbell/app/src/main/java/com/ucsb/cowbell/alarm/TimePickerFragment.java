package com.ucsb.cowbell.alarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ucsb.cowbell.R;
import com.ucsb.cowbell.alarm.alarms.Alarm;
import com.ucsb.cowbell.alarm.alarms.AlarmStorage;
import com.ucsb.cowbell.alarm.alarms.AlarmUtil;
import com.ucsb.cowbell.fillblanks.cards.Card;
import com.ucsb.cowbell.fillblanks.cards.CardAdapter;
import com.ucsb.cowbell.fillblanks.cards.CardStorage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * DialogFragment for showing a TimePicker.
 */
public class TimePickerFragment extends DialogFragment {

    private Switch mFillBlankToggle;
    private Switch mMultipleChoiceToggle;
    private Switch mReactionGameToggle;

    private TimePicker mTimePicker;
    private AlarmStorage mAlarmStorage;
    private AlarmAddListener mAlarmAddListener;
    private AlarmUtil mAlarmUtil;

    public EditText mEditText;
    public int chosenGame = 2;


    public TimePickerFragment() {}

    public static TimePickerFragment newInstance() {

        return new TimePickerFragment();
    }

    /** sets up AlarmAddListener to be used by add alarm buttons */ 
    public void setAlarmAddListener(AlarmAddListener listener) {
        mAlarmAddListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_NoActionBar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAlarmStorage = new AlarmStorage(getActivity());
        mAlarmUtil = new AlarmUtil(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker_alarm);
        mMultipleChoiceToggle = (Switch) view.findViewById(R.id.multiple_choice_switch);
        mFillBlankToggle = (Switch) view.findViewById((R.id.Fill_Blank_Switch));
        mReactionGameToggle = (Switch) view.findViewById(R.id.react_switch);
        mEditText = (EditText) view.findViewById(R.id.nameOfAlarm);

        mFillBlankToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CardStorage cardStorage = new CardStorage(getActivity(),"card_preferences");
                CardAdapter mCardAdapter = new CardAdapter(getActivity(), cardStorage.getCards());
                if (mCardAdapter.getItemCount() < 3) {
                    mFillBlankToggle.setEnabled(false);
                    Toast.makeText(getContext(), "You need at least 3 fill-in the blank cards to select this option", Toast.LENGTH_SHORT).show();
                } else {
                    if (isChecked) {
                        mMultipleChoiceToggle.setEnabled(false);
                        mReactionGameToggle.setEnabled(false);
                        chosenGame = 0;
                    } else {
                        mMultipleChoiceToggle.setEnabled(true);
                        mReactionGameToggle.setEnabled(true);
                        chosenGame = 2;
                    }
                }
            }
        });

       mMultipleChoiceToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CardStorage mCardStorage = new CardStorage(getActivity(), "mc_card");
                Set<Card> mSet = mCardStorage.getCards();
                List<Card> mList = new ArrayList<Card>(mSet);
                if (mList.size() < 4) {
                    mMultipleChoiceToggle.setEnabled(false);
                    Toast.makeText(getContext(), "You need at least 4  multiple choice cards to select this option", Toast.LENGTH_SHORT).show();
                } else {
                    if (isChecked) {
                        mFillBlankToggle.setEnabled(false);
                        mReactionGameToggle.setEnabled(false);
                        chosenGame = 1;

                    } else {
                        mFillBlankToggle.setEnabled(true);
                        mReactionGameToggle.setEnabled(true);
                        chosenGame = 2;
                    }
                }
            }
        });

        mReactionGameToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mMultipleChoiceToggle.setEnabled(false);
                    mFillBlankToggle.setEnabled(false);
                    chosenGame = 2;
                }
                else {
                    mMultipleChoiceToggle.setEnabled(true);
                    mFillBlankToggle.setEnabled(true);
                    chosenGame = 2;
                }
            }
        });

        Button buttonOk = (Button) view.findViewById(R.id.button_ok_time_picker);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar alarmTime = mAlarmUtil
                        .getNextAlarmTime(mTimePicker.getHour(), mTimePicker.getMinute());
                Alarm alarm = mAlarmStorage
                        .saveAlarm(alarmTime.get(Calendar.MONTH), alarmTime.get(Calendar.DATE),
                                alarmTime.get(Calendar.HOUR_OF_DAY), alarmTime.get(Calendar.MINUTE), chosenGame, mEditText.getText().toString());
                String alarmSavedString = getActivity()
                        .getString(R.string.alarm_saved, alarm.hour, alarm.minute);
                Toast.makeText(getActivity(), alarmSavedString, Toast.LENGTH_SHORT).show();
                if (mAlarmAddListener != null) {
                    mAlarmAddListener.onAlarmAdded(alarm);
                }
                dismiss();
            }
        });
        Button buttonCancel = (Button) view.findViewById(R.id.button_cancel_time_picker);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    public interface AlarmAddListener {
        void onAlarmAdded(Alarm alarm);
    }
}
