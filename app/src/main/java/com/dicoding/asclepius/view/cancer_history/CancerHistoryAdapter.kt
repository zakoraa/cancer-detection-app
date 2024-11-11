package com.dicoding.asclepius.view.cancer_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.CancerHistory
import com.dicoding.asclepius.databinding.CardHistoryItemBinding

class CancerHistoryAdapter(private val listCancerHistory: List<CancerHistory>) :
    RecyclerView.Adapter<CancerHistoryAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            CardHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val cancerHistory = listCancerHistory[position]
        cancerHistory.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = listCancerHistory.size

    inner class ListViewHolder(private val binding: CardHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cancerHistory: CancerHistory) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(cancerHistory.image)
                    .placeholder(R.drawable.ic_place_holder)
                    .into(previewImageView)

                resultTextView.text = cancerHistory.result
            }

        }
    }
}
