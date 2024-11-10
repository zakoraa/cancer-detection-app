package com.dicoding.asclepius.data.di

import android.content.Context
import com.dicoding.asclepius.data.local.database.CancerHistoryRoomDatabase
import com.dicoding.asclepius.data.local.repository.CancerHistoryRepository
import com.dicoding.asclepius.data.remote.repository.ArticleRepository
import com.dicoding.asclepius.data.remote.retrofit.ArticleConfig
import com.dicoding.asclepius.data.remote.retrofit.ArticleService
import com.dicoding.asclepius.helper.AppExecutors

object Injection {
    fun provideRepository(context: Context): CancerHistoryRepository {
        val database = CancerHistoryRoomDatabase.getDatabase(context)
        val dao = database.cancerHistoryDao()
        val appExecutors = AppExecutors()
        return CancerHistoryRepository.getInstance(dao)
    }

    fun provideArticleRepository(): ArticleRepository {
        val articleService = ArticleConfig.getApiService()
        return ArticleRepository.getInstance(articleService)
    }
}