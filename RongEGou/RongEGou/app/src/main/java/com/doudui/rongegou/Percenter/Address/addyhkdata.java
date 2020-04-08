package com.doudui.rongegou.Percenter.Address;

public class addyhkdata {
    String id, text,type;//type来区分省市区
    String select;
    public addyhkdata(String id, String text, String type,String select){
        this.id = id;
        this.text = text;
        this.type = type;
        this.select = select;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
