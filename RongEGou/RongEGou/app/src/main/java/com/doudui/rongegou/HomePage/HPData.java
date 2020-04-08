package com.doudui.rongegou.HomePage;

public class HPData {
    String id, text;
    boolean isselect;
    public HPData( String id,String  text,boolean isselect){
        this.id = id;
        this.text = text;
        this.isselect = isselect;
    }

    public boolean isIsselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
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
