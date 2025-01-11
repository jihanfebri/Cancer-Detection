package com.dicoding.asclepius.view.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.RecordEntity
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.repository.RecordRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ResultViewModel(private val repository: RecordRepository) : ViewModel() {

    fun insert(recordEntity: RecordEntity) {
        viewModelScope.launch { repository.insert(recordEntity) }
    }

    fun delete(recordEntity: RecordEntity) {
        viewModelScope.launch { repository.delete(recordEntity) }
    }

    fun saveRecord(record: RecordEntity) {
        viewModelScope.launch {
            repository.insert(record)
        }
    }
}