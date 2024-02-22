package com.example.grootclub.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.grootclub.data.Remote.Repository.SignIn.SignInRepository

class SignInViewModelFactory(private val signInRepository: SignInRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(signInRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
