package com.dicoding.asclepius.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.di.Injection
import com.dicoding.asclepius.data.local.repository.CancerHistoryRepository
import com.dicoding.asclepius.data.remote.repository.ArticleRepository
import com.dicoding.asclepius.view.cancer_history.CancerHistoryViewModel
import com.dicoding.asclepius.view.health_news.HealthNewsViewModel

class ViewModelFactory private constructor(
    private val cancerHistoryRepository: CancerHistoryRepository,
    private val articleRepository: ArticleRepository,
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CancerHistoryViewModel::class.java)) {
            return CancerHistoryViewModel(cancerHistoryRepository) as T
        } else if (modelClass.isAssignableFrom(HealthNewsViewModel::class.java)) {
            return HealthNewsViewModel(articleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                    Injection.provideArticleRepository()
                )
            }.also { instance = it }
    }
}