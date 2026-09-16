package com.example.test;

public class Item {
   String name;
   int price;
    public static void main(String[] args){
        //物品-血瓶
        Item blood_bottle = new Item();
        blood_bottle.name = "血瓶";
        blood_bottle.price = 50;
        //物品-草鞋
        Item grass_shoes = new Item();
        grass_shoes.name = "草鞋";
        grass_shoes.price = 300;
        //物品-长剑
        Item long_sword = new Item();
        long_sword.name = "长剑";
        long_sword.price = 350;
    }
}
