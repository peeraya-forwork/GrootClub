package com.example.appzaza.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.grootclub.components.dialog.DialogProgress

/**
 * คลาสพื้นฐานสำหรับอินสแตนซ์ [Fragment] ทั้งหมด
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    abstract val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding : VB
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindLayout.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView(savedInstanceState)
    }

    abstract fun prepareView(savedInstanceState: Bundle?)

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    fun showProgressDialog(message: String? = null, @ColorRes progressColor: Int = -1) {
        //fix : java.lang.IllegalStateException: Fragment AbsenceCalendarScreen{595fb51} (7263ff01-1917-418c-bb04-e4cae378f0f5)} has not been attached yet.
        if (!isAdded) return
        var dialog = childFragmentManager.findFragmentByTag(DialogProgress::class.java.simpleName)
        if (dialog != null && dialog is DialogProgress) {
            dialog.setProgressColor(progressColor)
//            dialog.setMessage(message)
        } else {
            dialog = DialogProgress().newInstance(message,progressColor)
            childFragmentManager.beginTransaction()
                .add(dialog, DialogProgress::class.java.simpleName)
                .commitAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        }
    }

    fun hideProgressDialog() {
        childFragmentManager.findFragmentByTag(DialogProgress::class.java.simpleName)?.also { dialog ->
            childFragmentManager.fragments
            childFragmentManager.beginTransaction()
                .remove(dialog)
                .commitAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        }
    }
}