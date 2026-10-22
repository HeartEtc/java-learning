package com.example.game;

import java.util.*;

public class GameEngine {

    /** 核心：给定手牌，算最优点数（>21 = 爆牌） */
    public int handValue(List<Card> hand) {
        // TODO 1: 遍历手牌，累加点数，A 先按 11 记
        // TODO 2: 统计 A 的个数
        // TODO 3: while (总和>21 且 A数>0) { 总和-=10; A数--; }
        // TODO 4: return 总和
        int allValue = 0;
        int Acount = 0;
        for (Card handCard : hand){
            allValue += handCard.pointValue();
            if (handCard.rank() == Rank.ACE){
                Acount ++;
            }
        }

        while (allValue > 21 && Acount != 0){
            allValue = allValue - 10;
            Acount --;
        }

        return allValue;
    }

    /** 一局：抽牌 → 要/停 → 返回分数 */
    public int play(Deck deck, Scanner sc) {
        // TODO 5: 建一个空手牌 List<Card>
        // TODO 6: 循环：
        //   抽一张 → 加进手牌 → 用 handValue 算点数 → 打印
        //   点数>21 → 打印爆牌 → return 0
        //   问"还要牌吗 y/n" → 输入 n → 打印停牌 → return 点数
        List<Card> hand = new ArrayList<>();
        while (true) {
            Card c = deck.draw();
            hand.add(c);
            int value = handValue(hand);

            System.out.println("抽到一张" + c.Suit() + c.rank() + "!");
            System.out.println("您现在手牌点数为" + value);

            if (value > 21) {
                System.out.println("可惜,相加超过21点,您输了!");
                return 0;
            }

            while (true) {
                System.out.println("还要抽吗?\n 1 for yes\n 0 for no");
                int option = sc.nextInt();
                if (option == 1) {
                    break;
                } else if (option == 0) {
                    System.out.println("停牌\n本局 " + value + " 分");
                    return value;
                } else {
                    System.out.println("无效选项，请输入 1 或 0!");
                }
            }
        }
    }
}