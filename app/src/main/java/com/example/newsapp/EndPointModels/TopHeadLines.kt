package com.example.newsapp.EndPointModels

import com.example.newsapp.Model.Article
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TopHeadLines {
    @SerializedName("status")
    @Expose
    var status: String = ""

    @SerializedName("totalResults")
    @Expose
    var totalResult: Int = 0

    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null

}