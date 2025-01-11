package com.dicoding.asclepius.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecordEntity::class], version = 2)
abstract class AsclepiusDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: AsclepiusDatabase? = null

        fun getDatabase(context: Context): AsclepiusDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context.applicationContext).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AsclepiusDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AsclepiusDatabase::class.java, "asclepius_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
