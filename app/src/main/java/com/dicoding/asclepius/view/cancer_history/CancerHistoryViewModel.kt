package com.dicoding.asclepius.view.cancer_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.CancerHistory
import com.dicoding.asclepius.data.local.repository.CancerHistoryRepository
import kotlinx.coroutines.launch

class CancerHistoryViewModel(private val cancerHistoryRepository: CancerHistoryRepository) : ViewModel() {

    fun getAllCancerHistories() = cancerHistoryRepository.getAllCancerHistories()

    fun insertCancerHistory(cancerHistory: CancerHistory) {
        viewModelScope.launch {
            cancerHistoryRepository.insert(cancerHistory)
        }
    }

}