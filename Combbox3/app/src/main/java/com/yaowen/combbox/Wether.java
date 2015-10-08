package com.yaowen.combbox;

/**
 * Created by HelloWorld on 2015/10/7.
 */
public class Wether {
    private String city, wendu, shidu, fengxiang, zhuangtai;

    public Wether(String city, String wendu, String shidu,String fengxiang, String zhuangtai) {
        this.city = city;
        this.wendu = wendu;
        this.shidu=shidu;
        this.fengxiang = fengxiang;
        this.zhuangtai = zhuangtai;
    }

    public String getCity() {
        return city;
    }

    public void setCity() {
        this.city = city;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu() {
        this.wendu = wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu() {
        this.shidu = shidu;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang() {
        this.fengxiang = fengxiang;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai() {
        this.zhuangtai = zhuangtai;
    }

}

