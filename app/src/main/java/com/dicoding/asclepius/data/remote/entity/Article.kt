package com.dicoding.asclepius.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Article(

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("source")
    val source: Source? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

)

data class Source(
    @field:SerializedName("name")
    val name: String? = null,
)
