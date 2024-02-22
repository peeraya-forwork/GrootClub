package com.example.grootclub.utils.sharedpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.grootclub.MainActivity
import com.example.grootclub.ui.signIn.SignInActivity

class SharedPreference(val context: Context) {

    companion object {
        const val PREFS_NAME = "SharedPreference"
        const val IS_LOGIN: String = "isLogin"
        const val KEY_MAIL: String = "mail"
        const val KEY_PASS: String = "pass"
        const val KEY_TOKEN: String = "token"
        const val KEY_ID: String = "id"
        const val IS_REMEMBER: String = "isRemember"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPref.edit()

    fun clearSharedPreference() {
        editor.clear()
        editor.apply()
    }

    fun setValueString(keyName: String, text: String) {
        editor.putString(keyName, text)
        editor.apply()
    }

    fun getValueString(keyName: String): String? {
        return sharedPref.getString(keyName, "")
    }

    fun setValueBoolean(keyName: String, boolean: Boolean) {
        editor.putBoolean(keyName, boolean)
        editor.apply()
    }

    fun getValueBoolean(keyName: String): Boolean {
        return sharedPref.getBoolean(keyName, false)
    }

    fun removeValue(keyName: String) {
        editor.remove(keyName)
        editor.apply()
    }

    fun createLoginSession(str: String, pass: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_MAIL, str)
        editor.putString(KEY_PASS, pass)
        editor.commit()
    }

    fun checkLogin() {
        if (!this.isLogIn()) {
            val i = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(i)
        }
    }

    fun getUserDetails(): HashMap<String, String> {
        val data: Map<String, String> = HashMap<String, String>()
        (data as HashMap)[KEY_MAIL] = sharedPref.getString(KEY_MAIL, null).toString()
        data.put(KEY_PASS, sharedPref.getString(KEY_PASS, null).toString())

        return data
    }

    fun logoutUser() {
        editor.clear()
        editor.commit()

        val i = Intent(context, SignInActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

    fun isLogIn(): Boolean {
        return sharedPref.getBoolean(IS_LOGIN, false)
    }

}