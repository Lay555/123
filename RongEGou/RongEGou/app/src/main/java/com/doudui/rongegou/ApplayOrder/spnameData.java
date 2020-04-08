package com.doudui.rongegou.ApplayOrder;

public class spnameData {
    String id, name,goodsid;

    public spnameData(String id, String name,String goodsid) {
        this.id = id;
        this.name = name;
        this.goodsid = goodsid;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
