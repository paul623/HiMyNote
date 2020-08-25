package com.paul.himynote.Tools;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SpHelper {
    Context context;
    SharedPreferences sp;

    public SpHelper(Context context,String dataName) {
        this.context = context;
        sp=context.getSharedPreferences(dataName,Context.MODE_PRIVATE);
    }
    public void saveInt(String key,int value){
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public void saveString(String key,String value){
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public void saveBoolean(String key,boolean value){
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public void saveFloat(String key,float value){
        SharedPreferences.Editor editor=sp.edit();
        editor.putFloat(key,value);
        editor.apply();
    }
    public void saveStringSet(String key, Set<String> value){
        SharedPreferences.Editor editor=sp.edit();
        editor.putStringSet(key,value);
        editor.apply();
    }
    public int getInt(String key,int d_value){
        return sp.getInt(key,d_value);
    }
    public String getString(String key,String d_value){
        return sp.getString(key,d_value);
    }
    public Boolean getBoolean(String key,Boolean d_value){
        return sp.getBoolean(key,d_value);
    }
    public Set<String> getStringSet(String key){
        return sp.getStringSet(key,null);
    }
    public Float getFloat(String key,float d_value){
        return sp.getFloat(key,d_value);
    }
}