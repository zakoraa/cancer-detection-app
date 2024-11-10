package com.dicoding.asclepius.data.remote.entity

import com.google.gson.annotations.SerializedName

data class Article(
    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("source")
    val source: Source? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("content")
    val content: String? = null
)

data class Source(
    @field:SerializedName("name")
    val name: String? = null,
)
