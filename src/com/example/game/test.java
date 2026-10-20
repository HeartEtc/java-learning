package com.example.game;

import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Deck deck = new Deck();
        GameEngine engine = new GameEngine();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== 单人 21点 ===");
        int score = engine.play(deck, sc);
        System.out.println("本局得分: " + score);

        sc.close();
    }
}