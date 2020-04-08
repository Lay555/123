package com.doudui.rongegou.Percenter.zijinpac;

public class zijindata {
    String name, time, numstr,status;

    public zijindata(String name, String time, String numstr,String status) {
        this.name = name;
        this.time = time;
        this.status = status;
        this.numstr = numstr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumstr() {
        return numstr;
    }

    public void setNumstr(String numstr) {
        this.numstr = numstr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
