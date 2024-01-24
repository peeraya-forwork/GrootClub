package com.example.grootclub.components.navigator

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Navigator {
    companion object {
        fun activityToActivity(context: Context, destinationActivity: Class<*>) {
            val intent = Intent(context, destinationActivity)
            context.startActivity(intent)
        }

        fun fragmentToFragment(
            fragmentManager: FragmentManager,
            containerId: Int,
            fragment: Fragment,
            addToBackStack: Boolean = false
        ) {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            if (addToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }

        fun activityToFragment(
            fragmentManager: FragmentManager,
            containerId: Int,
            fragment: Fragment,
            addToBackStack: Boolean = false
        ) {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(containerId, fragment)
            if (addToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }
    }
}