package com.example.test;

public class find {
    public static void main(String[] args) {

        int[] a = new int[5];
        a[0] = (int) (Math.random() * 100);
        a[1] = (int) (Math.random() * 100);
        a[2] = (int) (Math.random() * 100);
        a[3] = (int) (Math.random() * 100);
        a[4] = (int) (Math.random() * 100);
        int b = a[0];
        System.out.println("数组中的各个随机数是:" + a[0] + " " + a[1] + " " + a[2] + " " + a[3] + " " + a[4]);
        for (int each : a) {
            if(b < each){
                b = each;
            }
        }
        System.out.println("现在数组中的最大数是:" + b);
    }
}
