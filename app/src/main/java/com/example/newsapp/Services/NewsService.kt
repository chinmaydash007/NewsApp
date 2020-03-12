package com.example.newsapp.Services

import com.example.newsapp.EndPointModels.TopHeadLines
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsService {
    @GET("top-headlines")
    fun getHeadLines(@QueryMap parameters:Map<String,String> ): Call<TopHeadLines>
}