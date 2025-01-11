package com.dicoding.asclepius.view.record

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.RecordEntity
import com.dicoding.asclepius.data.repository.RecordRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecordViewModel(private val repository: RecordRepository) : ViewModel() {
    private val _record = MutableStateFlow<List<RecordEntity>>(emptyList())
    val resultsFlow: StateFlow<List<RecordEntity>> = _record

    init {
        loadResults()
    }

    private fun loadResults() {
        viewModelScope.launch {
            repository.getAll().collect {
                _record.value = it
            }
        }
    }

    fun deleteRecord(record: RecordEntity) {
        viewModelScope.launch {
            repository.delete(record)
            loadResults() // Refresh the list after deletion
        }
    }
}
