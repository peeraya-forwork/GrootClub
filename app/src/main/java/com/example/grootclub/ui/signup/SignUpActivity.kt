package com.example.grootclub.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.grootclub.R
import com.example.grootclub.components.dialog.MyDialog
import com.example.grootclub.databinding.ActivitySignUpBinding
import com.example.grootclub.ui.signIn.SignInActivity
import com.example.grootclub.utils.Utils

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initToolbar()
        setOnClicks()
    }

    private fun initView() {

    }

    private fun initToolbar() {
        binding.toolbar.btnIconBack.setOnClickListener {
            finish()
        }
    }

    private fun setOnClicks() {
        binding.btnSignUp.setOnClickListener {
            checkInputData()
        }
        binding.imvEyePass.setOnClickListener {
            showHidePass(it)
        }
        binding.imvEyeConfirm.setOnClickListener {
            showHidePass(it)
        }
    }

    private fun showHidePass(view: View) {

        when (view.id) {
            binding.imvEyePass.id -> {
                if (binding.etPass.transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                    binding.imvEyePass.setImageResource(R.drawable.ic_visibility)
                    //Show Password
                    binding.etPass.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    Utils.setCursorPointer(binding.etPass)
                } else {
                    binding.imvEyePass.setImageResource(R.drawable.ic_visibility_off)
                    //Hide Password
                    binding.etPass.transformationMethod = PasswordTransformationMethod.getInstance()
                    Utils.setCursorPointer(binding.etPass)
                }
            }

            binding.imvEyeConfirm.id -> {
                if (binding.etConfirmPass.transformationMethod.equals(
                        PasswordTransformationMethod.getInstance()
                    )
                ) {
                    binding.imvEyeConfirm.setImageResource(R.drawable.ic_visibility)
                    binding.etConfirmPass.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    Utils.setCursorPointer(binding.etConfirmPass)
                } else {
                    binding.imvEyeConfirm.setImageResource(R.drawable.ic_visibility_off)
                    binding.etConfirmPass.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    Utils.setCursorPointer(binding.etConfirmPass)
                }
            }

        }

    }

    private fun checkInputData() {
        val myDialog = MyDialog()
        val fullName = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPass.text.toString()
        val confirmPassword = binding.etConfirmPass.text.toString()

        if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (!Utils.isValidEmail(binding.etEmail)) {
                myDialog.showDialog(this, "Alert", "Please fill in correct email information.")
            } else if (password == confirmPassword) {
                myDialog.showDialogOKCallBack(this, "Alert", "SignUp Success"){
                    val intent = Intent(this, SignInActivity::class.java)
                    // ตั้งค่า flags เพื่อลบประวัติการเรียกของ SignInActivity
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            } else {
                myDialog.showDialog(this, "Alert", "Passwords don't match")
            }
        } else {
            myDialog.showDialog(this, "Alert", "Please fill in complete information.")
        }
    }
}