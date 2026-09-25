package com.example.library;

public class Admin extends User{
    /*extend from user的管理员,所有方法沿用user的defult
    目前与 Reader 只在名称上有区别：权限判断由 Library 完成（if (user instanceof Admin)），
    本类目前不提供方法。
     */

    public Admin(String id, String name, int loanLimit){
        super(id, name, loanLimit);
    }

    @Override
    public String getRoleName() { return "管理员"; }
}
