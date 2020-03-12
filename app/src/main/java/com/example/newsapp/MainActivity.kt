package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsapp.EndPointModels.TopHeadLines
import com.example.newsapp.Services.NewsService
import com.example.newsapp.Services.ServiceBuilder
import com.example.newsapp.Utils.log
import com.example.newsapp.Utils.showtoast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var newsService: NewsService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsService = ServiceBuilder.buildService(NewsService::class.java)
        var queryMap = HashMap<String, String>()
        queryMap.put("country", "us")
        queryMap.put("apiKey", resources.getString(R.string.news_api_key))
        var getHeadLines: Call<TopHeadLines> = newsService.getHeadLines(queryMap)
        getHeadLines.enqueue(object : Callback<TopHeadLines> {

            override fun onResponse(call: Call<TopHeadLines>, response: Response<TopHeadLines>) {


            }

            override fun onFailure(call: Call<TopHeadLines>, t: Throwable) {
                log(t.message.toString())
                this@MainActivity.showtoast(t.message.toString())
            }

        })


    }
}
