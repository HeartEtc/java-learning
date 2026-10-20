package com.example.game;


public record Card(Suit Suit, Rank rank) {
    public int pointValue() {
        return rank.value();
    }
}

