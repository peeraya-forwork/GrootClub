package com.example.grootclub.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grootclub.data.Remote.Repository.Profile.ProfileRepository
import com.example.grootclub.data.Request.Profile.ReqUpdateProfile
import com.example.grootclub.data.Response.Profile.RespCurrentUser
import com.example.grootclub.data.Response.Profile.RespUpdateUser
import kotlinx.coroutines.launch

class ProfileVM(private val repository: ProfileRepository) : ViewModel() {
    private val _currentUserResponse = MutableLiveData<RespCurrentUser>()
    val currentUserResponse: LiveData<RespCurrentUser> = _currentUserResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _updateUserResponse = MutableLiveData<RespUpdateUser>()
    val updateUserResponse: LiveData<RespUpdateUser> = _updateUserResponse

    fun postCurrentUser() {
        viewModelScope.launch {
            try {
                val result = repository.postCurrentUser()
                _currentUserResponse.postValue(result.body())
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.toString())
            }
        }
    }


    fun updateUser(reqUpdateProfile: ReqUpdateProfile) {
        viewModelScope.launch {
            try {
                val result = repository.updateUser(reqUpdateProfile)
                _updateUserResponse.postValue(result.body())
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.toString())
            }
        }
    }
}