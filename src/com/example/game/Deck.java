package com.example.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        refill();
    }

    private void refill() {
        cards.clear();
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                cards.add(new Card(s, r));
            }
        }
        Collections.shuffle(cards);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            refill();
        }
        return cards.remove(cards.size() - 1);
    }

    public int size() { return cards.size(); }


}

