package com.example.test;

public class Greeter1 {
    public static void main(String[] args) {
        Greeter g = new Greeter();
        System.out.println(g.greet());
        System.out.println(g.greet("张三"));
        System.out.println(g.greet("张三", 3));
    }

}

class Greeter{
    public String greet() {
        return "你好,陌生人";
    }                       // 返回 "你好，陌生人"
    public String greet(String name) {
        return "你好," + name;
    }            // 返回 "你好，张三"
    public String greet(String name, int times) {
        String result = "";
        for (int i = 0; i < times;i++){
            if (i != 0) {
                result += " ";        // 什么时候才需要补这个空格？
            }
            result += "你好," + name;
        }
        return result;
    } // 重复 times 次，用空格连起来
}