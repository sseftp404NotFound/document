package com.slsnotfound.doucument.doucument.Model;

import lombok.Data;

@Data
public class Role {
    private int uid;
    private String password;
    private int role;
    private String question;
    private String answer;

    public Role(){
    }
}
