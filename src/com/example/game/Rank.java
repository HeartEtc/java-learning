package com.example.game;


public enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5),
    SIX(6), SEVEN(7), EIGHT(8), NINE(9),
    TEN(10), JACK(10), QUEEN(10), KING(10),
    ACE(11);   // ← 10/J/Q/K 都是 10

    private final int value;

    Rank(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}

