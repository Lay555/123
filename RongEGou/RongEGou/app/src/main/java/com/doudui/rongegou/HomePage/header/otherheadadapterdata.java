package com.doudui.rongegou.HomePage.header;

public class otherheadadapterdata {
    String id,url, txt,zhuname,zhuid;
    public otherheadadapterdata ( String id,String url,String  txt,String zhuname,String zhuid){
        this.id = id;
        this.url = url;
        this.txt = txt;
        this.zhuname = zhuname;
        this.zhuid = zhuid;
    }

    public String getZhuid() {
        return zhuid;
    }

    public void setZhuid(String zhuid) {
        this.zhuid = zhuid;
    }

    public String getZhuname() {
        return zhuname;
    }

    public void setZhuname(String zhuname) {
        this.zhuname = zhuname;
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

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
