package com.example.grootclub.utils

import android.text.InputFilter
import android.widget.EditText
import android.widget.Toast

class EditTextValidator(private val editText: EditText) {

    fun setup() {
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            val input = source.toString()
            val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8}\$".toRegex()
            if (pattern.matches(input)) {
                null
            } else {
                Toast.makeText(editText.context, "Please enter valid data.", Toast.LENGTH_SHORT).show()
                ""
            }
        }
        val filters = arrayOf(filter)
        editText.filters = filters
    }

    fun isValid(): Boolean {
        val input = editText.text.toString()
        val pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8}\$".toRegex()
        return pattern.matches(input)
    }
}
