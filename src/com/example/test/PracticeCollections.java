package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class PracticeCollections {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(
                List.of("算法导论", "Java编程思想", "CSAPP", "数据库系统概念", "操作系统导论"));
        System.out.println("目前大小:" + list.size());
        list.remove(2);
        System.out.println("目前大小:" + list.size());
        System.out.println("--- 普通 for ---");
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            System.out.println(i+1 + "." + s);
        }
        System.out.println("--- 增强 for ---");
        for (String s : list) {
            System.out.println(s);
        }
        boolean include = false;
        for (String s : list) {
            if (s.equals("算法导论")) {
                include = true;
                break;
            }
        }
        System.out.println("包含\"算法导论\": " + include);
        System.out.println("列表内容:" + list);

    }
}

