package com.example.grootclub.utils

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.EditText

object Utils {
    fun setCursorPointer(edit: EditText){
        edit.setSelection(edit.text.length)
    }

    fun checkValuesEditText(edit: EditText) : Boolean {
        return edit.text.isEmpty()
    }

    fun isValidEmail(binding: EditText): Boolean {
        val edtEmail: String = binding.text.toString().trim()
        // นิพจน์ทั่วไปสำหรับการตรวจสอบรูปแบบอีเมล
        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}".toRegex()
        return emailRegex.matches(edtEmail)
    }

    inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
    }

    inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
        SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
}
