package com.dicoding.asclepius.data.repository

import com.dicoding.asclepius.data.local.RecordDao
import com.dicoding.asclepius.data.local.RecordEntity
import kotlinx.coroutines.flow.Flow

class RecordRepository(private val dao: RecordDao) {
    fun getAll(): Flow<List<RecordEntity>> = dao.getAll()
    fun getById(id: Int): Flow<RecordEntity> = dao.getRecordEntityById(id)
    suspend fun insert(recordEntity: RecordEntity) = dao.insert(recordEntity)
    suspend fun delete(recordEntity: RecordEntity) = dao.delete(recordEntity)

    companion object {
        @Volatile
        private var instance: RecordRepository? = null

        fun getInstance(dao: RecordDao): RecordRepository =
            instance ?: synchronized(this) {
                instance ?: RecordRepository(dao).also { instance = it }
            }
    }
}
