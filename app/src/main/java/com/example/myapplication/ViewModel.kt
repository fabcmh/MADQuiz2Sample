package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MRTViewModel(private val repository: MRTRepository) : ViewModel() {
    val MRTList: LiveData<List<MRT>> = repository.getAllData

    suspend fun addMRT(mrt: MRT) {
        repository.addMRT(mrt)
    }

    suspend fun deleteAll() {
        repository.deleteAll()
    }

}

class MRTViewModelFactory(private val repository: MRTRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MRTViewModel::class.java)) {
            return MRTViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}