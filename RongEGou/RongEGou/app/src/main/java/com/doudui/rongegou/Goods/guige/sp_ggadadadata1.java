package com.doudui.rongegou.Goods.guige;

public class sp_ggadadadata1 {

    boolean t_sele;
    String id, text, iscanoncl;

    public sp_ggadadadata1(boolean t_sele, String id, String text, String iscanoncl) {
        this.t_sele = t_sele;
        this.id = id;
        this.text = text;
        this.iscanoncl = iscanoncl;//是否可以点击
    }

    public String getIscanoncl() {
        return iscanoncl;
    }

    public void setIscanoncl(String iscanoncl) {
        this.iscanoncl = iscanoncl;
    }

    public boolean isT_sele() {
        return t_sele;
    }

    public void setT_sele(boolean t_sele) {
        this.t_sele = t_sele;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
