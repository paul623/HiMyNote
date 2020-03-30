package com.paul.himynote.Manager;

import android.content.Context;

import com.paul.himynote.Model.NoteBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NoteBeanManager {
    public static List<NoteBean> getAll(){
        return LitePal.order("addDate").find(NoteBean.class);
    }
    public static NoteBean getRealNoteBean(NoteBean noteBean){
        List<NoteBean> noteBeans=getAll();
        for(NoteBean i:noteBeans){
            if(i.equals(noteBean)){
                return i;
            }
        }
        return new NoteBean();
    }
    public static List<String> getThemesName(){
        List<NoteBean> noteBeans=getAll();
        HashSet<String> hashSet=new HashSet<>();
        for(NoteBean i:noteBeans){
            hashSet.add(i.getTheme());
        }

        return new ArrayList<>(hashSet);
    }
    /**
     * 计算两个日期之间的差值
     * B-A
     * */
    public static long countDayToInt(String dateA,String dateB){
        Calendar a = Calendar.getInstance(),
                b = Calendar.getInstance();
        String[] splitdate = dateA.split("-");
        a.set(Integer.parseInt(splitdate[0]), Integer.parseInt(splitdate[1]) - 1, Integer.parseInt(splitdate[2]));
        String[] splitdateB = dateB.split("-");
        b.set(Integer.parseInt(splitdateB[0]), Integer.parseInt(splitdateB[1]) - 1, Integer.parseInt(splitdateB[2]));
        long diffDays = (b.getTimeInMillis() - a.getTimeInMillis())
                / (1000 * 60 * 60 * 24);
        return diffDays;
    }

}
