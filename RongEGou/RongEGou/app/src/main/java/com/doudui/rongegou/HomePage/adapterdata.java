package com.doudui.rongegou.HomePage;

public class adapterdata {
    String id, url, name, price,isyugou;

    public adapterdata(String id, String url, String name, String price,String isyugou) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.price = price;
        this.isyugou = isyugou;
    }

    public String getIsyugou() {
        return isyugou;
    }

    public void setIsyugou(String isyugou) {
        this.isyugou = isyugou;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
