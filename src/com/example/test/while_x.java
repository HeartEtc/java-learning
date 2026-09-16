package com.example.test;
import java.util.Scanner;
public class while_x {
    public static void main(String[] args) {
        Scanner get_number = new Scanner(System.in);
        System.out.println("请输入一个整数");
        int n = get_number.nextInt();
        int a = n-1;
        while (a!=1){
            n = a * n;
            a--;
        }
        System.out.println("阶乘是:"+n);
    }
}
