package com.doudui.rongegou.HomePage.phb;

public class phbdata {
    String url, name, gge, pos;

    public phbdata(String url, String name, String gge, String pos) {
        this.url = url;
        this.name = name;
        this.gge = gge;
        this.pos = pos;
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

    public String getGge() {
        return gge;
    }

    public void setGge(String gge) {
        this.gge = gge;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
