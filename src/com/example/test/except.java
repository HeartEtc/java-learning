package com.example.test;

public class except {

    public static void main(String[] args) {
        for (int j = 0; j < 100; j++) {
            if(0 == j % 3) {
                continue;
            }
            else if (0 == j % 5) {
                continue;
            }
            System.out.println(j);
        }
    }

}
