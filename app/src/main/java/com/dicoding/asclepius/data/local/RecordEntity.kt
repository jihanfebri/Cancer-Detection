package com.dicoding.asclepius.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "record")
@Parcelize
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "image_uri")
    val imageUri: String,
    @ColumnInfo(name = "label")
    val label: String,
    @ColumnInfo(name = "confidence")
    val confidence: Float,
) : Parcelable
