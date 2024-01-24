package com.example.grootclub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.grootclub.data.ProductModel

class SharedViewModel : ViewModel() {
    private val selectedStadium = MutableLiveData<ProductModel.Stadium>()

    fun selectStadium(stadium: ProductModel.Stadium) {
        selectedStadium.value = stadium
    }

    fun getSelectedStadium(): LiveData<ProductModel.Stadium> {
        return selectedStadium
    }
}
