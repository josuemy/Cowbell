package com.ucsb.cowbell.fillblanks.cards;


import android.annotation.TargetApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by Josue on 4/14/16.
 */
public class Card implements Comparable<Card>, Parcelable{
    public int id;
    public String title;
    public String description;

    // default constructor
    public Card(){}

    /*
    * Constructor when Parcelable needs to be read and turned into a Card
    * */
    protected Card(Parcel in){
        id = in.readInt();
        title = in.readString();
        description = in.readString();
    }

    // two arg constructor
    private Card(String title, String description){
        this.title = title;
        this.description = description;
    }

    // Parcelable reader
    public static final Parcelable.Creator<Card> CREATOR = new Parcelable.Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    // required function for Parcelable
    @Override
    public int describeContents() {return 0;}

    // writes Card to Parcel
    @Override
    public void writeToParcel (Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
    }

    // Converts Card to JSON string
    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("title", title);
            jsonObject.put("description", description);
        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
        return jsonObject.toString();
    }

    // Converts JSON string to Card
    public static Card fromJson(String string) {
        JSONObject jsonObject;
        Card card = new Card();
        try {
            jsonObject = new JSONObject(string);
            card.id = jsonObject.getInt("id");
            card.title = jsonObject.getString("title");
            card.description = jsonObject.getString("description");
        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: " + string);
        }

        return card;
    }

    // Converts Card to String
    @Override
    public String toString() {
        return "Card{" +
                "id" + id +
                "title=" + title +
                ", description=" + description +
                '}';
    }

    // Override equals method for hashing
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        Card card = (Card) o;
        return id==card.id &&
                title.equals(card.title) &&
                description.equals(card.description);

    }

    // Override hashCode method for use in CardStorage
    @Override
    @TargetApi(21)
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    // Override compareTo method for use in CardAdapter
    @Override
    public int compareTo(@NonNull Card other) {
        Card card = new Card(title,description);

        return card.title.compareToIgnoreCase(other.title);

    }


}