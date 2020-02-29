package com.example.newsapp.Model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("title") private var _title: String?,
    @SerializedName("body") private val _body: String? = "",
    val viewCount: Int = 0,
    val payWall: Boolean = false,
    @SerializedName("titleImage") private val _titleImage: String? = ""
) {
    var title
        get() = _title ?: throw IllegalArgumentException("Title is required")
    set(value){
        _title=value
    }
    val body
        get() = _body ?: ""
    val titleImage
        get() = _titleImage ?: ""
    init {
        this.title
    }
}