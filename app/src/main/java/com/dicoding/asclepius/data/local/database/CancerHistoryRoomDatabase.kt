package com.dicoding.asclepius.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.asclepius.data.local.entity.CancerHistory

@Database(entities = [CancerHistory::class], version = 1)
abstract class CancerHistoryRoomDatabase : RoomDatabase() {
    abstract fun cancerHistoryDao(): CancerHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: CancerHistoryRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): CancerHistoryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CancerHistoryRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CancerHistoryRoomDatabase::class.java, "cancer_history_database")
                        .build()
                }
            }
            return INSTANCE as CancerHistoryRoomDatabase
        }
    }
}