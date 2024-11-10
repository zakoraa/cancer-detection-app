package com.dicoding.asclepius.data.remote.retrofit

import com.dicoding.asclepius.data.remote.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("top-headlines?q=cancer&category=health&language=en")
    suspend fun getHealthNews(@Query("apiKey") apiKey: String): ArticleResponse
}