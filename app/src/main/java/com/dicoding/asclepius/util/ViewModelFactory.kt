package com.dicoding.asclepius.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.RecordRepository
import com.dicoding.asclepius.data.injection.DependencyInjection
import com.dicoding.asclepius.view.record.RecordViewModel
import com.dicoding.asclepius.view.result.ResultViewModel

class ViewModelFactory private constructor(
    private val repository: RecordRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(RecordViewModel::class.java) -> RecordViewModel(repository)
        modelClass.isAssignableFrom(ResultViewModel::class.java) -> ResultViewModel(repository)
        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    } as T

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(DependencyInjection.provideRepository(context)).also { instance = it }
            }
    }
}
