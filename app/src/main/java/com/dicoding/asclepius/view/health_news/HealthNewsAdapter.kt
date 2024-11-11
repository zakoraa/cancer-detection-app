package com.dicoding.asclepius.view.health_news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.entity.Article
import com.dicoding.asclepius.databinding.CardItemBinding

class HealthNewsAdapter(private val listArticle: List<Article>) :
    RecyclerView.Adapter<HealthNewsAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val article = listArticle[position]
        article.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = listArticle.size

    inner class ListViewHolder(private val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(imageView)
                nameTextView.text = article.source?.name
                titleTextView.text = article.title

                urlButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(article.url)
                    }
                    itemView.context.startActivity(intent)
                }
            }

        }
    }
}
