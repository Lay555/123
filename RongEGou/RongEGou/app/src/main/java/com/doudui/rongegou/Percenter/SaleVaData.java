package com.doudui.rongegou.Percenter;

public class SaleVaData {
    String name,sznumber,daysale,monsale;
    public SaleVaData(String name,String sznumber,String daysale,String monsale){
        this.name=name;
        this.sznumber=sznumber;
        this.daysale=daysale;
        this.monsale = monsale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSznumber() {
        return sznumber;
    }

    public void setSznumber(String sznumber) {
        this.sznumber = sznumber;
    }

    public String getDaysale() {
        return daysale;
    }

    public void setDaysale(String daysale) {
        this.daysale = daysale;
    }

    public String getMonsale() {
        return monsale;
    }

    public void setMonsale(String monsale) {
        this.monsale = monsale;
    }
}
