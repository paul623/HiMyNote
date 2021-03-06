package com.paul.himynote.Manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingManager {
    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private String dataBase="setting_manager_data";
    //背景
    private String BG_PATH="bg_path";
    private String bg_path;
    //Alpha
    private String ALPHA_NUMBER="alpha_number";
    private Float alpha_number;
    //WebDav账户
    private String str_username="str_username";
    private String str_password="str_password";
    private String str_server="str_server";
    String username;
    String password;
    String userServer;
    //用户名设置
    private String str_head_username="str_head_username";
    private String head_username;

    //头像
    private String str_headicon_path="str_headicon_path";
    private String headicon_path;


    public String getBg_path() {
        return bg_path;
    }

    public void setBg_path(String bg_path) {
        this.bg_path = bg_path;
        editor.putString(BG_PATH,bg_path);
        editor.apply();
    }

    public Float getAlpha_number() {
        return alpha_number;
    }

    public void setAlpha_number(Float alpha_number) {
        this.alpha_number = alpha_number;
        editor.putFloat(ALPHA_NUMBER,alpha_number);
        editor.apply();
    }

    public SettingManager(Context context) {
        this.context = context;
        sp=context.getSharedPreferences(dataBase,Context.MODE_PRIVATE);
        editor=sp.edit();

        alpha_number=sp.getFloat(ALPHA_NUMBER,1.0f);
        bg_path=sp.getString(BG_PATH,"");
        username=sp.getString(str_username,"");
        userServer=sp.getString(str_server,"");
        password=sp.getString(str_password,"");
        head_username=sp.getString(str_head_username,"Little Prince");
        headicon_path=sp.getString(str_headicon_path,"");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        editor.putString(str_username,username);
        editor.apply();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        editor.putString(str_password,password);
        editor.apply();
    }

    public String getUserServer() {
        if(userServer==null||userServer.equals("")){
            return "https://dav.jianguoyun.com/dav/";
        }
        return userServer;
    }

    public void setUserServer(String userServer) {
        this.userServer = userServer;
        editor.putString(str_server,userServer);
        editor.apply();
    }

    public boolean canSync() {
        username=sp.getString(str_username,"");
        userServer=sp.getString(str_server,"");
        password=sp.getString(str_password,"");
        if(userServer.equals("")||username.equals("")||password.equals("")){
            return false;
        }
        return true;
    }

    public String getHead_username() {
        return head_username;
    }

    public void setHead_username(String head_username) {
        this.head_username = head_username;
        editor.putString(str_head_username,head_username);
        editor.apply();
    }

    public String getHeadicon_path() {
        return headicon_path;
    }

    public void setHeadicon_path(String headicon_path) {
        this.headicon_path = headicon_path;
        editor.putString(str_headicon_path,headicon_path);
        editor.apply();
    }
}
