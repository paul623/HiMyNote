package com.paul.himynote.Manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.paul.himynote.Model.KindsBean;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.Tools.ColorPool;
import com.paul.himynote.Tools.DateUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.Calendar;
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

    public static void saveAll(List<NoteBean> noteBeans){
        LitePal.deleteAll(NoteBean.class);
        for(NoteBean i:noteBeans){
           NoteBean noteBean=new NoteBean();
           noteBean.setColor(i.getColor());
           noteBean.setFlag(i.isFlag());
           noteBean.setContent(i.getContent());
           noteBean.setColorID(i.getColorID());
           noteBean.setFinishDate(i.getFinishDate());
           noteBean.setEndDate(i.getEndDate());
           noteBean.setTheme_number(i.getTheme_number());
           noteBean.setTheme(i.getTheme());
           noteBean.setAddDate(i.getAddDate());
           noteBean.setTitle(i.getTitle());
           noteBean.save();
        }
    }
    public static NoteBean getDefaultNotes(Context context){
        SharedPreferences sp=context.getSharedPreferences("NoteBeanManager",Context.MODE_PRIVATE);
        if(sp.getBoolean("flag",true)){
            NoteBean noteBean=new NoteBean();
            noteBean.setTitle("默认笔记");
            noteBean.setTheme("星月记");
            noteBean.setContent("欢迎使用本软件\n长按移动位置，左滑右滑删除\n支持WebDav,下拉刷新\n可设置背景图片，调整透明度\n点击左上角星月记可生成分享图");
            noteBean.setAddDate(DateUtils.getCurDate());
            noteBean.setEndDate(DateUtils.getCurDate());
            noteBean.setColor(ColorPool.getColor());
            noteBean.save();
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean("flag",false);
            editor.apply();
            return noteBean;
        }else {
            return null;
        }
    }

    /**
     * 获取类别
     * */
    public static List<KindsBean> getKinds(){
        List<KindsBean> kindsBeans=new ArrayList<>();
        List<String> names=getThemesName();
        for(String i:names){
            List<NoteBean> noteBeans=LitePal.where("theme = ?",i).find(NoteBean.class);
            kindsBeans.add(new KindsBean(noteBeans));
        }
        return kindsBeans;
    }
}
