package com.example.test;

public class two_wei {
    public static void main(String[] args) {

        int[][] a = new int[5][5];
        int b = 0;
        int x = 0;
        int y = 0;
        for(int i = 0;i < 5;i++){
            for(int j = 0;j < 5;j++){
                a[i][j] = (int) (Math.random() * 100) + 1;
                System.out.print(a[i][j] + " ");
                if (a[i][j] > b){
                    b = a[i][j];
                    x = i + 1;
                    y = j + 1;
                }
            }
            System.out.println(" ");
        }

        System.out.println("现在数组中的最大数是:"+ b);
        System.out.println("坐标是:"+ x +"行"+ y +"列");
    }
}
