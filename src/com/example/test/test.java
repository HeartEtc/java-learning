package com.example.test;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner get_date = new Scanner(System.in);
        System.out.println("今天是?");
        for (int j = 0; j < 5; ) {
            boolean breakout = false;
            System.out.println("请键入今天周几(错误次数过多会导致系统退出)");
            int a = get_date.nextInt();
            if (a > 7) {
                    System.out.println("请输入1-7之间的数字!");
                    j++;
            }
            else {
                int i = 5;
                String k = a <= i ? "工作日" : "休息日";
                System.out.println("今天是" + k);
                breakout = true;
            }
            if (breakout) break;
        }
    }

}

