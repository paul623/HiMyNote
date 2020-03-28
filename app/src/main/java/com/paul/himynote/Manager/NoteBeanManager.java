package com.paul.himynote.Manager;

import android.content.Context;

import com.paul.himynote.Model.NoteBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NoteBeanManager {
    public static List<NoteBean> getAll(){
        return LitePal.findAll(NoteBean.class);
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
}
