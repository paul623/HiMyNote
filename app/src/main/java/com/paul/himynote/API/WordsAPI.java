package com.paul.himynote.API;

import com.paul.himynote.Model.WordsBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WordsAPI {
    @GET("ciba/api.php")
    Call<WordsBean> getJson(@Query("type")String type);
}
