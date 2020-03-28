package com.paul.himynote.Model;

import com.paul.himynote.Tools.DateUtils;

import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.Objects;

/**
 * 基础Bean类
 * 2020年3月25日19:10:57
 * Author Paul
 * */
public class NoteBean extends LitePalSupport {
    //事件标题
    private String title;
    //事件主题
    private String theme;
    private int theme_number;
    //事件颜色
    private int color;
    //事件内容
    private String content;
    //事件日期
    private String addDate;
    //事件完成日期
    private String finishDate;
    //事件结束日期
    private String endDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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

    @Override
    public String toString() {
        return "NoteBean{" +
                "title='" + title + '\'' +
                ", theme='" + theme + '\'' +
                ", theme_number=" + theme_number +
                ", color='" + color + '\'' +
                ", content='" + content + '\'' +
                ", addDate='" + addDate + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteBean noteBean = (NoteBean) o;
        return theme_number == noteBean.theme_number &&
                Objects.equals(title, noteBean.title) &&
                Objects.equals(theme, noteBean.theme) &&
                Objects.equals(color, noteBean.color) &&
                Objects.equals(content, noteBean.content) &&
                Objects.equals(addDate, noteBean.addDate) &&
                Objects.equals(finishDate, noteBean.finishDate) &&
                Objects.equals(endDate, noteBean.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, theme, theme_number, color, content, addDate, finishDate, endDate);
    }

    public NoteBean() {
        addDate= DateUtils.getCurDate();
    }
}
