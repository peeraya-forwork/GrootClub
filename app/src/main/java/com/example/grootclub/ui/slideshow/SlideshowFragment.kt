package com.example.grootclub.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.grootclub.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        setOnClicks()
        return root
    }

    private fun initView() {
        val slideshowViewModel = ViewModelProvider(this)[SlideshowViewModel::class.java]
//        val textView: TextView = binding.txtOurSupport
//        slideshowViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

    }

    private fun setOnClicks() {
        binding.itemCardView.cardStadium.setOnClickListener {
            Toast.makeText(requireContext(), "โหลลลล", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}