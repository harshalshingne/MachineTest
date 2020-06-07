package com.wednesday.machinetest.retrofit;

import com.wednesday.machinetest.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("search")
    Call<Result> getArtists(@Query("term") String term);
}
