package com.example.library;

public abstract class User {
    //抽象类,内部两个字段id        id,String final
    //               name       名称,String final
    //默认定义
    //       getId       获取Id
    //       getName     获取名称

    private final String id;
    private final String name;

    public User(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("invalid id!");
        }
        else if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("invalid name!");
        }
        else {
            this.id = id;
            this.name = name;
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getRoleName();

    @Override
    public String toString() {
        String s = getRoleName() + "{id='" + id + "', name='" + name + "'}";
        return s;
    }
}
