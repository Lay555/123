package com.doudui.rongegou.Order.neworder;

import com.doudui.rongegou.Order.recycada_datazi;

import java.util.List;

public class recycada_data {
    String id, bhao, ztai, allnum, allprice, shren, time, wuliuhao, wuliutype;
    List<recycada_datazi> list;
    String shouhou, zhuanhuabhao, zhuanhuashijian, quxiaoshijian,shouhuodz;

    public recycada_data(String id, String bhao, String ztai, String allnum, String allprice, String shren, String time, List<recycada_datazi> list,
                         String shouhou, String wuliuhao, String wuliutype, String zhuanhuabhao, String zhuanhuashijian, String quxiaoshijian,String shouhuodz) {
        this.id = id;
        this.bhao = bhao;
        this.ztai = ztai;
        this.allnum = allnum;
        this.allprice = allprice;
        this.shren = shren;
        this.time = time;
        this.list = list;
        this.shouhou = shouhou;
        this.wuliuhao = wuliuhao;
        this.wuliutype = wuliutype;
        this.zhuanhuabhao = zhuanhuabhao;
        this.zhuanhuashijian = zhuanhuashijian;
        this.quxiaoshijian = quxiaoshijian;
        this.shouhuodz = shouhuodz;
    }

    public String getShouhuodz() {
        return shouhuodz;
    }

    public void setShouhuodz(String shouhuodz) {
        this.shouhuodz = shouhuodz;
    }

    public String getQuxiaoshijian() {
        return quxiaoshijian;
    }

    public void setQuxiaoshijian(String quxiaoshijian) {
        this.quxiaoshijian = quxiaoshijian;
    }

    public String getZhuanhuabhao() {
        return zhuanhuabhao;
    }

    public void setZhuanhuabhao(String zhuanhuabhao) {
        this.zhuanhuabhao = zhuanhuabhao;
    }

    public String getZhuanhuashijian() {
        return zhuanhuashijian;
    }

    public void setZhuanhuashijian(String zhuanhuashijian) {
        this.zhuanhuashijian = zhuanhuashijian;
    }

    public String getWuliutype() {
        return wuliutype;
    }

    public void setWuliutype(String wuliutype) {
        this.wuliutype = wuliutype;
    }

    public String getWuliuhao() {
        return wuliuhao;
    }

    public void setWuliuhao(String wuliuhao) {
        this.wuliuhao = wuliuhao;
    }

    public String getShouhou() {
        return shouhou;
    }

    public void setShouhou(String shouhou) {
        this.shouhou = shouhou;
    }

    public List<recycada_datazi> getList() {
        return list;
    }

    public void setList(List<recycada_datazi> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBhao() {
        return bhao;
    }

    public void setBhao(String bhao) {
        this.bhao = bhao;
    }

    public String getZtai() {
        return ztai;
    }

    public void setZtai(String ztai) {
        this.ztai = ztai;
    }

    public String getAllnum() {
        return allnum;
    }

    public void setAllnum(String allnum) {
        this.allnum = allnum;
    }

    public String getAllprice() {
        return allprice;
    }

    public void setAllprice(String allprice) {
        this.allprice = allprice;
    }

    public String getShren() {
        return shren;
    }

    public void setShren(String shren) {
        this.shren = shren;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
