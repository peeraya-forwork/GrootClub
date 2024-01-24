package com.example.grootclub.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.grootclub.MainActivity
import com.example.grootclub.databinding.ActivityLoginBinding
import com.example.grootclub.databinding.ActivitySplashBinding
import com.example.grootclub.utils.sharedpreferences.SharedPreference

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharedPref: SharedPreference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initView()
        initSession()
    }

    private fun initView() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (!isFinishing) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)

    }

    private fun initSession() {
        sharedPref = SharedPreference(applicationContext)
        if (sharedPref.isLogIn()) {
            val i = Intent(applicationContext, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }
}