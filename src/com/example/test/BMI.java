package com.example.test;
import java.util.Scanner;
public class BMI {
    public static void main(String[] args) {
        Scanner get_number = new Scanner(System.in);
        System.out.println("BMI计算器");
        System.out.println("请输入体重:(单位为千克)");
        double weight = get_number.nextDouble();
        System.out.println("请输入身高:(单位为米)");
        double height = get_number.nextDouble();
        double bmi = weight/(height*height);
        System.out.println("当前的BMI是:"+bmi);
        String BMI = "无法判断";
        if(bmi < 18.5){
            BMI = "体重过轻";
        }
        else if (18.5 <= bmi && bmi < 24){
            BMI = "正常范围";
        }
        else if (24 <= bmi && bmi < 27){
            BMI = "体重过重";
        }
        else if (27 <= bmi && bmi < 30){
            BMI = "轻度肥胖";
        }
        else if (30 <= bmi && bmi < 35){
            BMI = "中度肥胖";
        }
        else if (35 <= bmi){
            BMI = "重度肥胖";
        }

        System.out.println("身体状态是:"+ BMI);

    }
}
