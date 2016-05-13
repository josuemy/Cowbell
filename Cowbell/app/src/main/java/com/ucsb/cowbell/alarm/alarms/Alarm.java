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

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

/**
 * Class represents a single alarm.
 */
public class Alarm implements Comparable<Alarm>, Parcelable {

    public int id;
    public int month;
    public int date;
    /** Integer as a 24-hour format */
    public int hour;
    public int minute;
    public int game;

    /** Creates Alarm instance */
    public Alarm() {}

    /** Creates Alarm instance using parameter type Parcel */
     
    protected Alarm(Parcel in) {
        id = in.readInt();
        month = in.readInt();
        date = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        game = in.readInt();
    }

    /** Returns game value of an (@link Alarm} instance */
    public int getGame() {
        return this.game;
    }

    /** Creates Container needed for data objects */
    
    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(month);
        parcel.writeInt(date);
        parcel.writeInt(hour);
        parcel.writeInt(minute);
        parcel.writeInt(game);
    }

    /**
     * Serialize the instance as a JSON String.
     * @return serialized JSON String.
     */
    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("month", month);
            jsonObject.put("date", date);
            jsonObject.put("hour", hour);
            jsonObject.put("minute", minute);
            jsonObject.put("game", game);
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
        return jsonObject.toString();
    }

    /**
     * Parses a Json string to an {@link Alarm} instance.
     *
     * @param string The String representation of an alarm
     * @return an instance of {@link Alarm}
     */
    public static Alarm fromJson(String string) {
        JSONObject jsonObject;
        Alarm alarm = new Alarm();
        try {
            jsonObject = new JSONObject(string);
            alarm.id = jsonObject.getInt("id");
            alarm.month = jsonObject.getInt("month");
            alarm.date = jsonObject.getInt("date");
            alarm.hour = jsonObject.getInt("hour");
            alarm.minute = jsonObject.getInt("minute");
            alarm.game = jsonObject.getInt("game");
        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: " + string);
        }

        return alarm;
    }
    /**
     * Creates a string from {@link Alarm} instance
     */
     
    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", month=" + month +
                ", date=" + date +
                ", hour=" + hour +
                ", minute=" + minute +
                ", game=" + game +
                '}';
    }
    
    /**
     * Compares two instances 
     * @return boolean
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Alarm)) {
            return false;
        }
        Alarm alarm = (Alarm) o;
        return id == alarm.id &&
                month == alarm.month &&
                date == alarm.date &&
                hour == alarm.hour &&
                minute == alarm.minute &&
                game == alarm.game;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, month, date, hour, minute, game);
    }

    @Override
    public int compareTo(@NonNull Alarm other) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Calendar otherCal = Calendar.getInstance();
        otherCal.set(Calendar.MONTH, other.month);
        otherCal.set(Calendar.DATE, other.date);
        otherCal.set(Calendar.HOUR_OF_DAY, other.hour);
        otherCal.set(Calendar.MINUTE, other.minute);
        return calendar.compareTo(otherCal);
    }
}
