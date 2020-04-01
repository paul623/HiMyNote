package com.paul.himynote.Model;

import com.paul.himynote.Tools.DateUtils;

import java.util.List;

public class KindsBean {
    //个数
    int number;
    //数据源
    List<NoteBean> data;
    //类别名称
    String theme;
    //倒计时
    String nearCountDay;
    //颜色
    int color;

    public KindsBean(List<NoteBean> data) {
        this.data = data;
        number=data.size();
        theme=data.get(0).getTheme();
        nearCountDay= DateUtils.getMinDays(data);
        color=data.get(0).getColorID();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<NoteBean> getData() {
        return data;
    }

    public void setData(List<NoteBean> data) {
        this.data = data;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getNearCountDay() {
        return nearCountDay;
    }

    public void setNearCountDay(String nearCountDay) {
        this.nearCountDay = nearCountDay;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
