package com.dicoding.asclepius.data.local.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.asclepius.data.ResultStatus
import com.dicoding.asclepius.data.local.database.CancerHistoryDao
import com.dicoding.asclepius.data.local.entity.CancerHistory

class CancerHistoryRepository private constructor(
    private val cancerHistoryDao: CancerHistoryDao
) {

    fun getAllCancerHistories(): LiveData<ResultStatus<List<CancerHistory>>> = liveData {
        emit(ResultStatus.Loading)
        try {
            val cancerHistories = cancerHistoryDao.getAllCancerHistories()

            val cancerHistoryList = cancerHistories.map { cancerHistory ->
                CancerHistory(
                    id = cancerHistory.id,
                    confidenceScore = cancerHistory.confidenceScore,
                    result = cancerHistory.result,
                    image = cancerHistory.image
                )
            }

            emit(ResultStatus.Success(cancerHistoryList))
        } catch (e: Exception) {
            Log.d("CancerHistoryRepository", "getAllCancerHistories: ${e.message.toString()} ")
            emit(ResultStatus.Error(e.message.toString()))
        }
    }

    suspend fun insert(cancerHistory: CancerHistory) {

        cancerHistoryDao.insert(cancerHistory)
    }

    companion object {
        @Volatile
        private var instance: CancerHistoryRepository? = null
        fun getInstance(
            cancerHistoryDao: CancerHistoryDao,
        ): CancerHistoryRepository =
            instance ?: synchronized(this) {
                instance ?: CancerHistoryRepository(cancerHistoryDao)
            }.also { instance = it }
    }
}