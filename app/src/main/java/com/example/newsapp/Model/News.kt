package com.example.newsapp.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class News {
    @SerializedName("status")
    @Expose
    private var status: String? = null

    @SerializedName("totalResult")
    @Expose
    private var totalResult: Int? = null

    @SerializedName("articles")
    @Expose
    private var article: List<Article>? = null

}