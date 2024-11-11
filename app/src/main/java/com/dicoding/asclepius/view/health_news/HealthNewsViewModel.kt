package com.dicoding.asclepius.view.health_news

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.remote.repository.ArticleRepository

class HealthNewsViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    fun getAllHealthNews() = articleRepository.getAllHealthNews()

}