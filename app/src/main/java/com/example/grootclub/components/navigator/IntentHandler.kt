package com.example.grootclub.components.navigator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.grootclub.R


class IntentHandler {
    // สำหรับสร้าง Intent และเรียก Activity
    fun startActivity(activity: AppCompatActivity, targetActivityClass: Class<*>, extras: Bundle? = null) {
        val intent = Intent(activity, targetActivityClass)
        extras?.let { intent.putExtras(it) }
        activity.startActivity(intent)
    }

    // สำหรับสร้าง Intent และเรียก Fragment
    fun startFragment(fragment: Fragment, targetFragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = fragment.requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_content_main, targetFragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}