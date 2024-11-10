package com.dicoding.asclepius.data.local.repository

import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.database.CancerHistoryDao
import com.dicoding.asclepius.data.local.entity.CancerHistory
import com.dicoding.asclepius.helper.AppExecutors

class CancerHistoryRepository private constructor(
    private val cancerHistoryDao: CancerHistoryDao,
    private val appExecutors: AppExecutors
) {

    fun getAllCancerHistories(): LiveData<List<CancerHistory>> =
        cancerHistoryDao.getAllCancerHistories()

    fun insert(cancerHistory: CancerHistory) {
        appExecutors.diskIO.execute {
            val cancerHistoryData = CancerHistory(
                image =
                cancerHistory.image,
                result = cancerHistory.result,
                confidenceScore = cancerHistory.confidenceScore,
            )
            cancerHistoryDao.insert(cancerHistoryData)
        }
    }

    companion object {
        @Volatile
        private var instance: CancerHistoryRepository? = null
        fun getInstance(
            cancerHistoryDao: CancerHistoryDao,
            appExecutors: AppExecutors
        ): CancerHistoryRepository =
            instance ?: synchronized(this) {
                instance ?: CancerHistoryRepository(cancerHistoryDao, appExecutors)
            }.also { instance = it }
    }
}