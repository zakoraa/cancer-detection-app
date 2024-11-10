package com.dicoding.asclepius.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.asclepius.data.local.entity.CancerHistory

@Dao
interface CancerHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cancer: CancerHistory)

    @Query("SELECT * from cancerHistory ORDER BY id ASC")
    suspend fun getAllCancerHistories(): List<CancerHistory>
}