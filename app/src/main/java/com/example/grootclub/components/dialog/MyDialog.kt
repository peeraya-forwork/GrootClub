package com.example.grootclub.components.dialog

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog

class MyDialog() {
    fun showDialog(context: Context, title: String, message: String) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    fun showDialogOKCallBack(context: Context, title: String, message: String, callBack: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                callBack()
            }
            .setCancelable(false)  // ปิดการให้ผู้ใช้แตะพื้นที่อื่น
            .create()
            .show()

    }


}