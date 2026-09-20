package com.example.library;

public class Reader extends User{
    //extend from user的读者,所有方法沿用user的defult

    public Reader(String id, String name){
        super(id, name);
    }

    @Override
    public String getRoleName() { return "读者"; }
}

