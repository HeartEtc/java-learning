package com.example.test;
import java.util.Scanner;

public class bliion_man {
    public static void main(String[] args) {
        Scanner get_number = new Scanner(System.in);
        System.out.println("请输入本金:");
        double p = get_number.nextDouble();
        System.out.println("请输入年利率");
        double r = get_number.nextDouble();
        System.out.println("请输入目标金额");
        double target = get_number.nextDouble();
        for (int i = 1; true; i++) {
            p = p * r + 12000;
            System.out.println("第" + i + "年财产为" + p + "元");
            if (p >= target) {
                System.out.println("第" + i + "年财产达成目标!");
                break;
            }
        }
    }
}
