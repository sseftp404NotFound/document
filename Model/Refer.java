package com.slsnotfound.doucument.doucument.Model;

import lombok.Data;

@Data
public class Refer {
    private int idrefer;
    private int uid;
    private String oname;
    private int rstatus;
    private int rid;
    private String rtext;

    public Refer(int uid,String oname,String rid){
        this.uid=uid;
        this.oname=oname;
        this.rstatus=0;
        if(rid!=null) {this.rid=Integer.parseInt(rid);}
        else { rid=null; }
    }
}
