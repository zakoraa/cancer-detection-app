package com.dicoding.asclepius.data.di

import android.content.Context
import com.dicoding.asclepius.data.local.database.CancerHistoryRoomDatabase
import com.dicoding.asclepius.data.local.repository.CancerHistoryRepository
import com.dicoding.asclepius.helper.AppExecutors

object Injection {
    fun provideRepository(context: Context): CancerHistoryRepository {
        val database = CancerHistoryRoomDatabase.getDatabase(context)
        val dao = database.cancerHistoryDao()
        val appExecutors = AppExecutors()
        return CancerHistoryRepository.getInstance(dao, appExecutors)
    }
}