package com.paul.himynote.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.audiofx.DynamicsProcessing;
import android.util.Log;

import com.paul.himynote.Model.KindsBean;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.Model.TagBean;
import com.paul.himynote.Tools.DateUtils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TagManager {
    List<TagBean> tagBeans;
    Context context;

    public TagManager(Context context){
        this.context=context;
        LitePal.initialize(context);
        tagBeans=new ArrayList<>();
        init();
    }
    private void checkOutDateTags(){
         /*
         * 淘汰规则：
         * 1.超过5天没有更新
         * 2.标签下没有任务了
         * */
         for(TagBean i:tagBeans){
             int day= (int) DateUtils.countDayToInt(i.getAddDate(),DateUtils.getCurDate());
             if(day>=5){
                 List<NoteBean> noteBeans=LitePal.where("theme = ?",i.getTagName()).find(NoteBean.class);
                 KindsBean kindsBean=new KindsBean(noteBeans);
                 if(kindsBean.getNumber()==0){
                     i.delete();
                     tagBeans.remove(i);
                 }
             }
         }

    }
    private void init(){
        String TAG="tag";
        String DATABASE="tag_database";
        SharedPreferences sp=context.getSharedPreferences(DATABASE,Context.MODE_PRIVATE);
        if(sp.getBoolean(TAG,true)){
            LitePal.deleteAll(TagBean.class);
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean(TAG,false);
            editor.apply();
            List<KindsBean> kinds = NoteBeanManager.getKinds();
            for(KindsBean i:kinds){
                TagBean tagBean=new TagBean(i.getTheme(),i.getNumber());
                tagBean.save();
                tagBeans.add(tagBean);
            }
        }else {
            tagBeans=LitePal.findAll(TagBean.class);
        }
        checkOutDateTags();
    }
    public List<TagBean> getTagBeans(){
        return tagBeans;
    }
    public void saveTagBean(NoteBean noteBean){
        TagBean tagBean= LitePal.where("tagName = ?",noteBean.getTheme()).findFirst(TagBean.class);
        if(tagBean!=null){
            int count=tagBean.getCount();
            count=count+1;
            tagBean.setCount(count);
            tagBean.save();
        }else {
            tagBean=new TagBean(noteBean.getTheme(),1);
            tagBean.save();
        }

    }
    public void removeTagBean(NoteBean noteBean){
        TagBean tagBean= LitePal.where("tagName = ?",noteBean.getTheme()).findFirst(TagBean.class);
        if(tagBean!=null){
            int count=tagBean.getCount();
            if(count!=1){
                count=count-1;
            }
            tagBean.setCount(count);
            tagBean.save();
        }
    }
    public  List<String> getThemesName(){
        HashSet<String> hashSet=new HashSet<>();
        for(TagBean i:tagBeans){
            hashSet.add(i.getTagName());
            Log.d("测试",i.getTagName());
        }
        return new ArrayList<>(hashSet);
    }

}
