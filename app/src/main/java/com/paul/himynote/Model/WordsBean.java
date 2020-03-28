package com.paul.himynote.Model;

import com.google.gson.annotations.SerializedName;

public class WordsBean {

    /**
     * code : 200
     * date : 2020-03-28
     * ciba : 真正的环保主义者应该知道，我们的世界不是继承自父辈的，而是从子孙后代那里借来的。
     * ciba-en : A true conservationist is a man who knows that the world is not given by his fathers, but borrowed from his children.
     * imgurl : https://edu-wps.ks3-cn-beijing.ksyun.com/image/e07fb1f1813bae9482ac0d209a0372ed.png
     */

    private String code;
    private String date;
    private String ciba;
    @SerializedName("ciba-en")
    private String cibaen;
    private String imgurl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCiba() {
        return ciba;
    }

    public void setCiba(String ciba) {
        this.ciba = ciba;
    }

    public String getCibaen() {
        return cibaen;
    }

    public void setCibaen(String cibaen) {
        this.cibaen = cibaen;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
