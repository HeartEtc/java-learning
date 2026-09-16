package com.example.test;
import java.util.Scanner;
public class caculate {
    public static void main(String[] args) {
        Scanner get_number = new Scanner(System.in);
        System.out.println("计算器");
        System.out.println("请依次输入数字");
        System.out.println("第一个数：");
        double a = get_number.nextDouble();
        System.out.println("第二个数：");
        double b = get_number.nextDouble();
        double c = a+b;
        System.out.println("结果为:"+c);
    }

}
