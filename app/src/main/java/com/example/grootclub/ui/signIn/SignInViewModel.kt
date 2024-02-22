package com.example.grootclub.ui.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grootclub.data.Remote.Repository.SignIn.SignInRepository
import com.example.grootclub.data.Request.SignIn.ReqSignIn
import com.example.grootclub.data.Response.SignIn.RespSignIn
import kotlinx.coroutines.launch
class SignInViewModel(private val signInRepository: SignInRepository) : ViewModel() {

    private val _signInResponse = MutableLiveData<RespSignIn>()
    val signInResponse: LiveData<RespSignIn> = _signInResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun postSignIn(reqSignIn: ReqSignIn) {
        viewModelScope.launch {
            try {
                val result = signInRepository.postSignIn(reqSignIn)
                _signInResponse.postValue(result.body())
            } catch (e: Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
        }
    }
}


