package com.example.grootclub.ui.coach

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import kotlinx.coroutines.launch

class CoachVM(private val repository: CoachRepository) : ViewModel() {
    private val _coachList = MutableLiveData<List<CoachListModelItem>>()
    val coachList: LiveData<List<CoachListModelItem>> = _coachList

    val readMoreEvent = MutableLiveData<CoachListModelItem>()

    fun fetchAllCoach() {
        viewModelScope.launch {
            try {
                val result = repository.getAllCoach()
                _coachList.postValue(result)
            } catch (e: Exception) {
                _coachList.postValue(emptyList())
            }
        }
    }
}