package com.example.test;
import java.util.Scanner;
public class compare {
    public static void main(String[] args) {
        Scanner get_number = new Scanner(System.in);
        System.out.println("比较器");
        System.out.println("请依次输入数字");
        System.out.println("第一个数：");
        int a = get_number.nextInt();
        System.out.println("第二个数：");
        int b = get_number.nextInt();
        System.out.println("比较"+a+">"+b+":"+(a>b));
        System.out.println("比较"+a+">="+b+":"+(a>=b));
        System.out.println("比较"+a+"<"+b+":"+(a<b));
        System.out.println("比较"+a+"<="+b+":"+(a<=b));
        System.out.println("比较"+a+"=="+b+":"+(a==b));
        System.out.println("比较"+a+"!="+b+":"+(a!=b));
    }
}
