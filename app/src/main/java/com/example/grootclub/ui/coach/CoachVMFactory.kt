package com.example.grootclub.ui.coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.ui.home.HomeViewModel

class CoachVMFactory(private val repository: CoachRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoachVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoachVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
