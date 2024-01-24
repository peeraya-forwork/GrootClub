package com.example.grootclub.ui.signIn

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grootclub.MainActivity
import com.example.grootclub.R
import com.example.grootclub.components.dialog.MyDialog
import com.example.grootclub.databinding.ActivityLoginBinding
import com.example.grootclub.ui.signup.SignUpActivity
import com.example.grootclub.utils.Utils
import com.example.grootclub.utils.sharedpreferences.SharedPreference

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPref: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        sharedPref = SharedPreference(applicationContext)

        setContentView(binding.root)

        initView()
        setOnClicks()
    }

    private fun initView() {
        val email = sharedPref.getValueString("email")
        if (email != null) {
            if (email.isNotEmpty()) {
                binding.etEmail.setText(email)
                binding.swRemember.isChecked = true
            } else {
                binding.swRemember.isChecked = false
            }
        }

        if (binding.swRemember.isChecked) {
            binding.etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val data = s.toString()
                    sharedPref.setValueString("email", data)
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }
    }

    private fun setOnClicks() {

        binding.btnSignIn.setOnClickListener {
            checkInputData()
        }

        binding.tvForgotPass.setOnClickListener {
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
            Toast.makeText(this, "ยังบ่ทันเฮ็ดจ้า พส.", Toast.LENGTH_SHORT).show()
        }

        binding.swRemember.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // การทำงานเมื่อสวิตช์ถูกเปิด
                sharedPref.setValueString("email", binding.etEmail.text.toString())
                checkRemember()
            } else {
                // การทำงานเมื่อสวิตช์ถูกปิด
                sharedPref.removeValue("email")
            }
        }

        binding.imvEyePass.setOnClickListener {
            showHidePass(it)
        }
    }

    private fun showHidePass(view: View) {

        when (view.id) {
            binding.imvEyePass.id -> {
                if (binding.etPass.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                    binding.imvEyePass.setImageResource(R.drawable.ic_visibility)
                    binding.etPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    Utils.setCursorPointer(binding.etPass)
                } else {
                    binding.imvEyePass.setImageResource(R.drawable.ic_visibility_off)
                    binding.etPass.transformationMethod = PasswordTransformationMethod.getInstance()
                    Utils.setCursorPointer(binding.etPass)
                }
            }
        }

    }

    private fun checkInputData() {
        val myDialog = MyDialog()

        if (binding.etEmail.text.isEmpty() || binding.etPass.text.isEmpty()) {
            myDialog.showDialog(this, "Alert", "Please fill in complete information.")
        } else if (!Utils.isValidEmail(binding.etEmail)) {
            myDialog.showDialog(this, "Alert", "Please fill in correct email information.")
        } else {
            sharedPref.createLoginSession(
                binding.etEmail.text.toString(),
                binding.etPass.text.toString()
            )
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun checkRemember() {

        if (binding.swRemember.isChecked) {
            binding.etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val data = s.toString()
                    sharedPref.setValueString("email", data)
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
