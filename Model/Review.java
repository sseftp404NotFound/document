package com.slsnotfound.doucument.doucument.Model;

import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class Review {
    private int rid;
    private int rproid;
    private String rpname;
    private int rstatus;
    private String rtext;
    private String rtime;

    public Review(int rproid,String rpname,int rstatus,String rtext){
        this.rproid=rproid;
        this.rpname=rpname;
        this.rtext=rtext;
        this.rstatus=rstatus;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.rtime=df.format(System.currentTimeMillis());
    }
}
