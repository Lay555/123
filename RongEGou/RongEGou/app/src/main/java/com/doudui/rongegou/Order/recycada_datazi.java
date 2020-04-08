package com.doudui.rongegou.Order;

public class recycada_datazi {
    String id,spurl,name,gge,price,num;

    public recycada_datazi(String id,String spurl,String name,String gge,String price,String num) {
        this.id = id;
        this.spurl = spurl;
        this.name = name;
        this.gge = gge;
        this.price = price;
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSpurl() {
        return spurl;
    }

    public void setSpurl(String spurl) {
        this.spurl = spurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGge() {
        return gge;
    }

    public void setGge(String gge) {
        this.gge = gge;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
