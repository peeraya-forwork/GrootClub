package com.example.grootclub.components.dialog

import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.grootclub.R
import com.example.grootclub.databinding.DialogProgressBinding

class DialogProgress : DialogFragment() {
    private var _binding: DialogProgressBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_MESSAGE = "msg"
        private const val KEY_PROGRESS_BAR_COLOR = "pColor"
    }

    fun newInstance(message: String? = null,
                    @ColorRes progressColor: Int = -1) = DialogProgress().apply {
        arguments = Bundle().apply {
            putString(KEY_MESSAGE, message)
            putInt(KEY_PROGRESS_BAR_COLOR, progressColor)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = false
        isCancelable = false
        setScreenTouchable(false)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogProgressBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(provideBackgroundDialog())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
//            setMessage(it.getString(KEY_MESSAGE))
            setProgressColor(it.getInt(KEY_PROGRESS_BAR_COLOR))
        }

    }
//    fun setMessage(message: String?) {
//        binding.xxx?.let {
//            it.text = message ?: ""
//            it.visibility = if (message?.isNotBlank() == true) View.VISIBLE else View.GONE
//        }
//    }

    fun setProgressColor(@ColorRes color: Int) {
        if (color == -1) return
        binding.progress.indeterminateDrawable?.setColorFilter(ContextCompat.getColor(requireContext(), color), PorterDuff.Mode.SRC_IN)
    }
    private fun provideBackgroundDialog() = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
    }
    override fun onDestroy() {
        Runtime.getRuntime().gc()
        setScreenTouchable(true)
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setScreenTouchable(touchable: Boolean) {
        when (touchable) {
            true -> activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            false -> activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

}