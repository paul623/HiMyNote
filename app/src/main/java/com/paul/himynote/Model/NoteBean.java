package com.paul.himynote.Model;

import android.graphics.Color;

import com.paul.himynote.Tools.ColorPool;
import com.paul.himynote.Tools.DateUtils;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 基础Bean类
 * 2020年3月25日19:10:57
 * Author Paul
 * */
public class NoteBean extends LitePalSupport implements Serializable {
    //事件标题
    private String title;
    //是否为过期事件
    private boolean flag;
    //事件主题
    private String theme;
    private int theme_number;
    //事件颜色
    private int colorID;
    //事件内容
    private String content;
    //事件日期
    private String addDate;
    //事件完成日期
    private String finishDate;
    //事件结束日期
    private String endDate;
    private String color;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title==null||title.equals("")){
            this.title="星月记";
        }else {
            this.title = title;
        }

    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getTheme_number() {
        return theme_number;
    }

    public void setTheme_number(int theme_number) {
        this.theme_number = theme_number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        if(color!=null&&!color.equals("")){
            colorID=Color.parseColor(color);
        }
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getColorID() {
        if(colorID==0){
            if(color==null||color.equals("")){
                setColor(ColorPool.getColor());
            }
            return Color.parseColor(color);
        }
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }

    public NoteBean() {
        addDate= DateUtils.getCurDate();
        flag=false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteBean noteBean = (NoteBean) o;
        return flag == noteBean.flag &&
                theme_number == noteBean.theme_number &&
                colorID == noteBean.colorID &&
                Objects.equals(title, noteBean.title) &&
                Objects.equals(theme, noteBean.theme) &&
                Objects.equals(content, noteBean.content) &&
                Objects.equals(addDate, noteBean.addDate) &&
                Objects.equals(finishDate, noteBean.finishDate) &&
                Objects.equals(endDate, noteBean.endDate) &&
                Objects.equals(color, noteBean.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, flag, theme, theme_number, colorID, content, addDate, finishDate, endDate, color);
    }

    @Override
    public String toString() {
        return "NoteBean{" +
                "title='" + title + '\'' +
                ", flag=" + flag +
                ", theme='" + theme + '\'' +
                ", theme_number=" + theme_number +
                ", colorID=" + colorID +
                ", content='" + content + '\'' +
                ", addDate='" + addDate + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", color='" + color + '\'' +
                '}';
    }


}
