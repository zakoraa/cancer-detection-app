package com.dicoding.asclepius.data.remote.response

import com.dicoding.asclepius.data.remote.entity.Article
import com.google.gson.annotations.SerializedName

data class ArticleResponse(
	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<Article?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)