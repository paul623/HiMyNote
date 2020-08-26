package com.paul.himynote.API;

public interface ILogin {
    public void register(String userName,String password);
    public boolean login(String userName,String password,boolean flag);
    public boolean canLogin();
    public String getUserName();
    public String getPassword();
}
