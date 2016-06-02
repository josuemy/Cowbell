/*
* Copyright 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.ucsb.cowbell.alarm.alarms;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.ucsb.cowbell.R;

/**
 * IntentService to set off an alarm.
 */


public class AlarmIntentService extends IntentService {

    public static final String ALARM_WENT_OFF_ACTION = AlarmIntentService.class.getName()
            + ".ALARM_WENT_OFF";
    public static final String ALARM_KEY = "alarm_instance";

    public AlarmIntentService() {
        super(AlarmIntentService.class.getName());
    }
    
    /**
     * Sets up notification options and actions for when the Alarm is set off
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = getApplicationContext();
        Alarm alarm = intent.getParcelableExtra(ALARM_KEY);



        NotificationManager notificationManager = context
                .getSystemService(NotificationManager.class);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_alarm_black_24dp)
                        .setCategory(Notification.CATEGORY_ALARM)
                        //.setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                        //http://stackoverflow.com/questions/15809399/android-notification-sound
                        //.setSound(Uri.parse("android.resource://"
                         //       + context.getPackageName() + "/" + R.raw.fur_elise))
                        .setContentTitle(context.getString(R.string.alarm_went_off, alarm.hour,
                                alarm.minute));

        notificationManager.notify(alarm.id, builder.build());

        AlarmStorage alarmStorage = new AlarmStorage(context);

        Intent gameIntent = new Intent(getApplicationContext(), PlayGameActivity.class);
        gameIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //http://stackoverflow.com/questions/5343544/send-a-variable-between-classes-through-the-intent
        gameIntent.putExtra("game_type", alarm.game);
        startActivity(gameIntent);

        alarmStorage.deleteAlarm(alarm);
        Intent wentOffIntent = new Intent(ALARM_WENT_OFF_ACTION);
        wentOffIntent.putExtra(ALARM_KEY, alarm);
        LocalBroadcastManager.getInstance(context).sendBroadcast(wentOffIntent);


    }
}
