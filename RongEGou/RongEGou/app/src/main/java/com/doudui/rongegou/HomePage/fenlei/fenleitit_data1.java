package com.doudui.rongegou.HomePage.fenlei;

public class fenleitit_data1 {
    String id, txt,isselect;

    public fenleitit_data1(String id, String txt,String isselect) {
        this.id = id;
        this.txt = txt;
        this.isselect = isselect;
    }

    public String getIsselect() {
        return isselect;
    }

    public void setIsselect(String isselect) {
        this.isselect = isselect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
