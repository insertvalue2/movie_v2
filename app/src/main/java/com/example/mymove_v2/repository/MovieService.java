package com.example.mymove_v2.repository;

import com.example.mymove_v2.models.YtsData;
import com.example.mymove_v2.utils.Define;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    // public static final <-- 생략
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Define.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("list_movies.json")
    Call<YtsData> repoContributors(
            @Query("sort_by") String sort_by,
            @Query("limit") int limit,
            @Query("page") int page
    );
}
