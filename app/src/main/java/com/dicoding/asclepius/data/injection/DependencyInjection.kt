package com.dicoding.asclepius.data.injection

import android.content.Context
import com.dicoding.asclepius.data.local.AsclepiusDatabase
import com.dicoding.asclepius.data.repository.RecordRepository

object DependencyInjection {

    @Volatile
    private var recordRepository: RecordRepository? = null

    fun provideRepository(context: Context): RecordRepository {
        return recordRepository ?: synchronized(this) {
            recordRepository ?: buildRepository(context.applicationContext).also { recordRepository = it }
        }
    }

    private fun buildRepository(context: Context): RecordRepository {
        val database = AsclepiusDatabase.getDatabase(context)
        return RecordRepository.getInstance(database.recordDao())
    }
}