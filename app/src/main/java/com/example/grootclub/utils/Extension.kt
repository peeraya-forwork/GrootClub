package com.example.grootclub.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.grootclub.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun initDatePickerCurrentDate20(context: Context, editText: TextView, ageTextView: TextView) {
    Locale.setDefault(Locale("en", "EN"))
    val calendar = Calendar.getInstance(Locale("en", "EN"))
    val selectedDate = Calendar.getInstance(Locale("en", "EN"))
    calendar.add(Calendar.YEAR, -20)
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val day = calendar[Calendar.DAY_OF_MONTH]

    val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        val thaiYear = year
        val fmDay = if (dayOfMonth < 10) "0${dayOfMonth}" else dayOfMonth
        val fmMonth = if (month < 9) "0${month + 1}" else month + 1
        val selectedDateStr = "$fmDay/${fmMonth}/$thaiYear"
        editText.text = selectedDateStr
        val dataSelectedDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDateStr)
        val age = calculateAge(dataSelectedDate!!)
        ageTextView.text = age.toString()
    }

    if (editText.text.isNotEmpty()) {
        val selectedDateStr = editText.text.toString()
        val dataSelectedDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDateStr)
        if (dataSelectedDate != null) {
            selectedDate.time = dataSelectedDate
        }
    }

    val dialog =
        DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day)
    dialog.updateDate(year, month, day)

    // Set maximum date to current date
    val maxDate = calendar.timeInMillis
    dialog.datePicker.maxDate = maxDate

    val title = "Calendar"
    val titleText = SpannableString(title)
    titleText.setSpan(
        ForegroundColorSpan(Color.BLACK),
        0,
        title.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    dialog.setTitle(titleText)

    dialog.updateDate(
        selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(
            Calendar.DAY_OF_MONTH
        )
    )


    dialog.show()
}

fun calculateAge(birthDate: Date): Int {
    val currentDate = Calendar.getInstance()
    val birthCalendar = Calendar.getInstance()
    birthCalendar.time = birthDate

    var age = currentDate.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

    if (currentDate.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
        age--
    }

    return age
}

fun setArrayAdapter(context: Context, arrayAdapter: Array<String>) =
    ArrayAdapter(
        context,
        androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
        arrayAdapter
    )
