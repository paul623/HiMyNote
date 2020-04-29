package com.paul.himynote.Model;

import com.paul.himynote.Tools.DateUtils;

import org.litepal.LitePalApplication;
import org.litepal.crud.LitePalSupport;

public class TagBean extends LitePalSupport {
    String tagName;
    String addDate;
    int count;

    public TagBean(String tagName,int count) {
        this.tagName = tagName;
        this.addDate = DateUtils.getCurDate();
        this.count=count;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
