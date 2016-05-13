package com.ucsb.cowbell.fillblanks.cards;


import android.content.Context;
import android.content.SharedPreferences;


import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Josue on 4/17/16.
 */

public class CardStorage {

    private static final String CARD_PREFERENCES_NAME = "card_preferences";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public SharedPreferences mSharedPreferences;

    /*
    * One arg constructor
    * Uses Shared Preferences to store Cards
    * */
    public CardStorage(Context context) {
        Context storageContext = context;
        mSharedPreferences = storageContext.getSharedPreferences(CARD_PREFERENCES_NAME,Context.MODE_PRIVATE);

    }

    // Converts Card to JSON and then saves it using Shared Preferences
    public Card saveCard(String title, String description){
        Card card = new Card();
        card.id = SECURE_RANDOM.nextInt();
        card.title = title;
        card.description = description;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(String.valueOf(card.id), card.toJson());
        editor.apply();
        return card;
    }

    // Retrieves the Set of saved Cards from Shared Preferences by converting it from JSON back to Card
    public Set<Card> getCards(){
        Set<Card> cards = new HashSet<>();
        for(Map.Entry<String, ?> entry : mSharedPreferences.getAll().entrySet()){
            cards.add(Card.fromJson(entry.getValue().toString()));
        }
        return cards;
    }

    // Removes a Card from the Set of saved Cards
    public void deleteCard (Card toBeDeleted) {
        for (Map.Entry<String, ?> entry : mSharedPreferences.getAll().entrySet()) {
            Card card = Card.fromJson(entry.getValue().toString());
            if (card.id == toBeDeleted.id){
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.remove(String.valueOf(card.id));
                editor.apply();
                return;
            }
        }
    }


}