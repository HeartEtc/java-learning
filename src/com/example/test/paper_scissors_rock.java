package com.example.test;
import java.util.Scanner;
public class paper_scissors_rock {
    public static void main(String[] args) {
        Scanner get_in = new Scanner(System.in);
        for (int i = 0 ; i < 5 ; i++) {
            System.out.println("石头剪刀布\n模式选择:\n1:双人对战\n2:人机对战");
            int a = get_in.nextInt();
            switch (a) {
                case 1 -> {
                    System.out.println("双人对战模式");
                    inner_1:
                    while (true) {
                        System.out.println("玩家1请出招:\n1:石头\n2:剪刀\n3:布");
                        int player1_in = get_in.nextInt();
                        for (int k = 0; k < 50; k++) {
                            System.out.println();
                        }
                        System.out.println("玩家2请出招:\n1:石头\n2:剪刀\n3:布");
                        int player2_in = get_in.nextInt();
                        int result = (player1_in - player2_in + 3) % 3;
                        switch (result) {
                            case 0 -> System.out.println("平局!");
                            case 1 -> System.out.println("玩家2赢了!");
                            case 2 -> System.out.println("玩家1赢了!");
                        }
                        System.out.println("是否继续?\n1:是\n2:否");
                        int c = get_in.nextInt();
                        switch (c) {
                            case 1 -> {
                                continue;
                            }
                            case 2 -> {
                                break inner_1;
                            }
                            default -> System.out.println("无效选项，默认继续");
                        }
                    }

                }
                case 2 -> {
                    System.out.println("人机对战模式");
                    inner_2:
                    while (true) {
                        System.out.println("请出招:\n1:石头\n2:剪刀\n3:布");
                        int player_in = get_in.nextInt();
                        int computer_in = (int) (Math.random() * 3) + 1;
                        int result = (player_in - computer_in + 3) % 3;
                        switch (result) {
                            case 0 -> System.out.println("平局!");
                            case 1 -> System.out.println("您输了");
                            case 2 -> System.out.println("您赢了!");
                        }
                        System.out.println("是否继续?\n1:是\n2:否");
                        int c = get_in.nextInt();
                        switch (c) {
                            case 1 -> {
                                continue;
                            }
                            case 2 -> {
                                break inner_2;
                            }
                            default -> System.out.println("无效选项，默认继续");
                        }
                    }
                }
                default -> System.out.println("无效选项! ");
            }
        }
        System.out.println("重复次数过多,程序退出");
    }
}
