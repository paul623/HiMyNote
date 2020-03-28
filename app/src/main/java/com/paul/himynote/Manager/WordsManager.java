package com.paul.himynote.Manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;

import com.paul.himynote.API.WordsAPI;
import com.paul.himynote.Model.WordsBean;
import com.paul.himynote.Tools.DateUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordsManager {
    Context context;
    String dataBase="wordsData";
    String str_date="str_date";
    String str_word="str_word";
    SharedPreferences sp;
    public WordsManager(Context context){
        this.context=context;
        sp=context.getSharedPreferences(dataBase,Context.MODE_PRIVATE);
    }
    public String getCurWord(){
        Log.d("测试","开始执行");
        if(canGet()){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("https://api.ooopn.com")
                    .addConverterFactory(GsonConverterFactory.create())//增加一个转换工厂
                    .build();
            WordsAPI api= retrofit.create(WordsAPI.class);
            Call<WordsBean> call=api.getJson("json");
            try {
                Response<WordsBean> execute = call.execute();
                WordsBean wordsBean=execute.body();
                sp.edit().putString(str_word,wordsBean.getCiba()).apply();
                sp.edit().putString(str_date,DateUtils.getCurDate()).apply();
                Log.d("测试","执行中。。。"+wordsBean.getCiba());
                return wordsBean.getCiba();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("测试","出错了,"+e);
                return "今天阳光很好~";
            }
        }else {
            Log.d("测试","出错了");
            return sp.getString(str_word,"今天阳光很好~");
        }
    }
    public boolean canGet(){
        String loc_date=sp.getString(str_date,"");
        if(!DateUtils.getCurDate().equals(loc_date)){
            return true;
        }else {
            return false;
        }
    }
}
