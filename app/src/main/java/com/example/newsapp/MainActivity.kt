package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.Model.Article
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        var gson: Gson = Gson()
//        var article1 = Article("chinmay",null,null)
//        var a: String = gson.toJson(article1)
//        Log.d("mytag", a)
//        var article: Article = gson.fromJson(a, Article::class.java)
//        article.title="lsnadna"

    }
}
