package com.example.test;

public class money {
    public static void main(String[] args) {
        System.out.println("天朝有一个乞丐姓洪，去天桥要钱");
        int money = 1;
        int all = 1;
        for (int j = 1; j < 10; j++){
            System.out.println("乞丐第"+j+"天要了"+money+"元");
            money=money*2;
            all=all+money;
        }
        System.out.println("十天总共要了"+all+"元");
    }


}
