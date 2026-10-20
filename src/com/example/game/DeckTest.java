package com.example.game;

public class DeckTest {
    static int pass = 0, fail = 0;

    static void eq(String what, Object expected, Object actual) {
        boolean ok = java.util.Objects.equals(expected, actual);
        if (ok) pass++; else fail++;
        System.out.printf("%s %-40s 期望=%-6s 实际=%s%n",
                ok ? "✅" : "❌", what, expected, actual);
    }

    public static void main(String[] args) {
        System.out.println("============扑克牌牌堆测试============");
        // 1. 初始 52 张
        Deck d = new Deck();
        eq("初始 52 张", 52, d.size());

        // 2. 抽 52 张 → 空；再抽 1 张 → 自动补一副（剩 51）
        for (int i = 0; i < 52; i++) d.draw();
        eq("抽完 52 张后 size=0", 0, d.size());
        d.draw();
        eq("再抽 1 张自动补牌（剩 51）", 51, d.size());

        // 3. 连抽 200 张不崩、不为 null
        Deck d2 = new Deck();
        boolean bad = false;
        for (int i = 0; i < 200; i++) {
            try {
                if (d2.draw() == null) { bad = true; break; }
            } catch (Exception e) {
                bad = true;
                System.out.println("第 " + i + " 张崩了: " + e);
                break;
            }
        }
        eq("连抽 200 张不崩不为 null", false, bad);

        // 4. 抽 5 张两次，顺序不同（洗牌生效）
        String a = five(new Deck());
        String b = five(new Deck());
        eq("两次抽 5 张顺序不同", false, a.equals(b));

        // 5. pointValue
        eq("TWO=2",   2,  new Card(Suit.红心,   Rank.TWO).pointValue());
        eq("TEN=10",  10, new Card(Suit.黑桃,   Rank.TEN).pointValue());
        eq("KING=10", 10, new Card(Suit.梅花,    Rank.KING).pointValue());
        eq("ACE=11",  11, new Card(Suit.方片, Rank.ACE).pointValue());

        // 6. record 判等
        eq("同牌相等",    true,  new Card(Suit.黑桃, Rank.ACE).equals(new Card(Suit.黑桃, Rank.ACE)));
        eq("不同花色不等", false, new Card(Suit.黑桃, Rank.ACE).equals(new Card(Suit.红心, Rank.ACE)));

        System.out.printf("%n结果：通过 %d / 失败 %d%n", pass, fail);
    }

    static String five(Deck d) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) sb.append(d.draw()).append(';');
        return sb.toString();
    }
}