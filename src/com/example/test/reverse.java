package com.example.test;

public class reverse {
    public static void main(String[] args) {

            int[] a = new int[5];
            a[0] = (int) (Math.random() * 100);
            a[1] = (int) (Math.random() * 100);
            a[2] = (int) (Math.random() * 100);
            a[3] = (int) (Math.random() * 100);
            a[4] = (int) (Math.random() * 100);
            int[] b = new int[5];
            System.out.println("数组中的各个随机数是:"+a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]);
            for (int j = 0; j < a.length-1; j++) {
                for (int i = j+1; i < a.length; i++) {
                    if(a[i]<a[j]){
                        int temp = a[j];
                        a[j] = a[i];
                        a[i] = temp;
                    }
                }
            }
            System.out.println("现在数组中的各个随机数是:"+a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]);
            for (int j = 0; j < a.length; j++) {
                for (int i = 0; i < a.length-j-1; i++) {
                    if(a[i]<a[i+1]){
                        int temp = a[i];
                        a[i] = a[i+1];
                        a[i+1] = temp;
                    }
                }

            }
            System.out.println("现在数组中的各个随机数是:"+a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]);
    }
}
