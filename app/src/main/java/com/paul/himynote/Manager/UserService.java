package com.paul.himynote.Manager;

import android.content.Context;

import com.paul.himynote.API.ILogin;
import com.paul.himynote.Tools.SpHelper;

public class UserService implements ILogin{
    static String USER_NAME="USER_NAME";
    static String PASSWORD="PASSWORD";
    static String USER_DATA="USER_DATABASE";
    static String RE_PASSWORD="RE_PASSWORD";
    SpHelper spHelper;
    Context context;

    public UserService(Context context) {
        this.context = context;
        spHelper=new SpHelper(context,USER_DATA);
    }
    public void register(String userName,String password){
        spHelper.saveString(USER_NAME,userName);
        spHelper.saveString(PASSWORD,password);
    }

    @Override
    public boolean login(String userName, String password, boolean flag) {
        if(spHelper.getString(USER_NAME,"").equals(userName)
                &&spHelper.getString(PASSWORD,"").equals(password)){
            spHelper.saveBoolean(RE_PASSWORD,flag);
            return true;
        }
        return false;
    }

    @Override
    public boolean canLogin() {
        return spHelper.getBoolean(RE_PASSWORD,false);
    }

    @Override
    public String getUserName() {
        return spHelper.getString(USER_NAME,"");
    }

    @Override
    public String getPassword() {
        return spHelper.getString(PASSWORD,"");
    }
}
