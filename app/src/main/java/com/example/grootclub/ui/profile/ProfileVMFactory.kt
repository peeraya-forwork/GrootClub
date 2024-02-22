package com.example.grootclub.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.grootclub.data.Remote.Repository.Profile.ProfileRepository

class ProfileVMFactory(private val profileRepository: ProfileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileVM::class.java)) {
            return ProfileVM(profileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}