package com.example.grootclub.utils

import android.os.SystemClock
import android.view.View

class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        val currentTime = SystemClock.elapsedRealtime()
        if (currentTime - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = currentTime
        onSafeClick(v)
    }
}
