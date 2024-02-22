package com.example.appzaza.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.example.grootclub.components.dialog.DialogProgress

/**
 * คลาสพื้นฐานสำหรับอินสแตนซ์ [AppCompatActivity] ทั้งหมด
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    abstract val bindLayout: (LayoutInflater) -> VB

    protected val binding: VB
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindLayout.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        prepareView(savedInstanceState)
    }

    abstract fun prepareView(savedInstanceState: Bundle?)

    // เพิ่ม FragmentManager ใน BaseActivity
    private val baseFragmentManager: FragmentManager
        get() = supportFragmentManager

    fun showProgressDialog(message: String? = null, @ColorRes progressColor: Int = -1) {
        // Fix: java.lang.IllegalStateException: Fragment AbsenceCalendarScreen{595fb51} has not been attached yet.
        if (!isFinishing) {
            var dialog = baseFragmentManager.findFragmentByTag(DialogProgress::class.java.simpleName)
            if (dialog != null && dialog is DialogProgress) {
                dialog.setProgressColor(progressColor)
            } else {
                dialog = DialogProgress().newInstance(message, progressColor)
                baseFragmentManager.beginTransaction()
                    .add(dialog, DialogProgress::class.java.simpleName)
                    .commitAllowingStateLoss()
                baseFragmentManager.executePendingTransactions()
            }
        }
    }

    fun hideProgressDialog() {
        baseFragmentManager.findFragmentByTag(DialogProgress::class.java.simpleName)?.also { dialog ->
            baseFragmentManager.beginTransaction()
                .remove(dialog)
                .commitAllowingStateLoss()
            baseFragmentManager.executePendingTransactions()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }



}