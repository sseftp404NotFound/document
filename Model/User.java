package com.slsnotfound.doucument.doucument.Model;

import lombok.Data;

@Data
public class User {
    private int uid;
    private String username;
    private String sex;
    private String birthday;
    private String password;
    private String phonenum;
    private String question;
    private String answer;
    private int role;
    private String name;
    private String address;
    private String company;
    private String profession;
    private String email;
    private String wechat;

    public User(String username,String sex,String birthday,String phonenum,String password,String question,String answer){
        this.username=username;
        this.sex=sex;
        this.birthday=birthday;
        this.phonenum=phonenum;
        this.password=password;
        this.question=question;
        this.answer=answer;
        this.role=0;
        this.name=null;
        this.address=null;
        this.company=null;
        this.profession=null;
        this.email=null;
        this.wechat=null;

    }
}
