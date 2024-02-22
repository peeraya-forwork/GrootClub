package com.example.grootclub.ui.booking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.R
import com.example.grootclub.databinding.FragmentBookingBinding
class BookingFragment : BaseFragment<FragmentBookingBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBookingBinding
        get() = FragmentBookingBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {

    }

}