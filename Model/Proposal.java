package com.slsnotfound.doucument.doucument.Model;

import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class Proposal {
    private int pid;
    private String pname;
    private int pwriterid;
    private String pwritername;
    private String ptext;
    private String psubmittime;
    private String pendtime;
    private int pstatus;
    private int psupport;
    private int preject;
    private String referrer;
    private String recorder;
    private String register;
    private String starter;

    public Proposal(String pname,int pwriterid,String pwritername,String ptext){
        this.pname=pname;
        this.pwriterid=pwriterid;
        this.pwritername=pwritername;
        this.ptext=ptext;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.psubmittime=df.format(System.currentTimeMillis());
        this.pendtime=df.format(System.currentTimeMillis()+2592000);//加一个月
        this.pstatus=0;
        this.psupport=0;
        this.preject=0;
        this.referrer=null;
        this.recorder=null;
        this.register=null;
        this.starter=null;
    }
}
