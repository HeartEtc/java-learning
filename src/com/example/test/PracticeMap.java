package com.example.test;

import java.util.HashMap;
import java.util.Map;

public class PracticeMap {
    public static void main(String[] args) {
    String[] hours = {"8", "8", "9", "8", "9", "10", "8", "9"};
    Map<String, Integer> map = new HashMap<>();
    for (String hour : hours){
        if (map.get(hour) == null){
            map.put(hour,1);
        }
        else{
            map.put(hour,map.get(hour) + 1);
        }
    }
    System.out.println(map);
    for (Map.Entry<String, Integer> entry : map.entrySet()){
        String key = entry.getKey();
        int value = entry.getValue();
        System.out.println("小时" + key + " → " + value + "次");
    }
        System.out.println(map.get("23"));
        System.out.println(map.get("23") + 1);
    }
}
