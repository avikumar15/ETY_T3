package com.example.spidertask3_v2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataInterface {

    @GET("{id}?fields=etymologies&strictMatch=false")
    Call<WordDetails> getData(@Path("id") String Sambhar, @Header("app_id") String app_id, @Header("app_key") String app_key);
}