package com.slsnotfound.doucument.doucument.Model;

import lombok.Data;

@Data
public class Rule {
    private int rid;
    private String rwritername;
    private String rname;
    private String rtext;

    public Rule(String rwritername,String rname,String rtext){
        this.rwritername=rwritername;
        this.rname=rname;
        this.rtext=rtext;
    }
}
