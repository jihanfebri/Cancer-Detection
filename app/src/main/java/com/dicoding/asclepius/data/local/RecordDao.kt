package com.dicoding.asclepius.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {

    @Query("SELECT * FROM record ORDER BY timestamp DESC")
    fun getAll(): Flow<List<RecordEntity>>

    @Query("SELECT * FROM record WHERE id = :id")
    fun getRecordEntityById(id: Int): Flow<RecordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg recordEntity: RecordEntity)

    @Delete
    suspend fun delete(recordEntity: RecordEntity)
}
