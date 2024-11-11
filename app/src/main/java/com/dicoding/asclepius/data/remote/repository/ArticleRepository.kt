package com.dicoding.asclepius.data.remote.repository

import android.util.Log
import com.dicoding.asclepius.data.remote.retrofit.ArticleService


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.asclepius.data.ResultStatus
import com.dicoding.asclepius.data.remote.entity.Article

class ArticleRepository private constructor(
    private val articleService: ArticleService,
) {

    fun getAllHealthNews(): LiveData<ResultStatus<List<Article>>> = liveData {
        emit(ResultStatus.Loading)
        try {
            val response = articleService.getHealthNews("68567837063a4227a3f6292ed35222ce")
            val articles = response.articles
            val healthNewsList = articles?.map { article ->
                Article(
                    title = article?.title,
                    publishedAt = article?.publishedAt,
                    urlToImage = article?.urlToImage,
                    url = article?.url,
                    source = article?.source,
                    content = article?.content,
                    description = article?.description
                )
            }
            emit(ResultStatus.Success(healthNewsList ?: emptyList()))
        } catch (e: Exception) {
            Log.d("ArticleRepository", "getAllHealthNews: ${e.message.toString()} ")
            emit(ResultStatus.Error(e.message.toString()))
        }

    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
           articleService: ArticleService,

            ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(articleService)
            }.also { instance = it }
    }
}