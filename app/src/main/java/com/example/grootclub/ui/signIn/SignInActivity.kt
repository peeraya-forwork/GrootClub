package com.example.grootclub.ui.signIn

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appzaza.base.BaseActivity
import com.example.grootclub.MainActivity
import com.example.grootclub.R
import com.example.grootclub.components.dialog.MyDialog
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.SignIn.SignInRepository
import com.example.grootclub.data.Request.SignIn.ReqSignIn
import com.example.grootclub.databinding.ActivityLoginBinding
import com.example.grootclub.ui.signup.SignUpActivity
import com.example.grootclub.utils.EditTextValidator
import com.example.grootclub.utils.Utils
import com.example.grootclub.utils.sharedpreferences.SharedPreference
import com.example.grootclub.utils.sharedpreferences.SharedPreference.Companion.KEY_ID
import com.example.grootclub.utils.sharedpreferences.SharedPreference.Companion.KEY_TOKEN

class SignInActivity : BaseActivity<ActivityLoginBinding>() {
    private lateinit var sharedPref: SharedPreference
    private lateinit var viewModel: SignInViewModel
    private lateinit var signInRepository: SignInRepository
    override val bindLayout: (LayoutInflater) -> ActivityLoginBinding
        get() = ActivityLoginBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        sharedPref = SharedPreference(applicationContext)

        val serviceInstance = ApiService().getService()
        signInRepository = SignInRepository(serviceInstance)
        viewModel = ViewModelProvider(this, SignInViewModelFactory(signInRepository))[SignInViewModel::class.java]

        initView()
        observeData()
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

    private fun observeData() {
        viewModel.signInResponse.observe(this) { result ->
            if (result != null) {
                if (result.token != null) {
                    sharedPref.createLoginSession(
                        result.payload?.user?.username.toString(),
                        result.payload?.user?.password.toString(),
                    )
                    sharedPref.setValueString(KEY_TOKEN, result.token)
                    sharedPref.setValueString(KEY_ID, result.payload?.user?.userId.toString())
                    hideProgressDialog()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    hideProgressDialog()
                    Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                }
            } else {
                hideProgressDialog()
                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.error.observe(this) { error ->
            hideProgressDialog()
            Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnClicks() {

        binding.btnSignIn.setOnClickListener {
            showProgressDialog()
            checkInputData()
        }

        binding.tvForgotPass.setOnClickListener {
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

        binding.SignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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
                    binding.etPass.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
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
        val email = binding.etEmail.text.toString()
        val passValidator = EditTextValidator(binding.etPass)

        if (email.isEmpty() || !passValidator.isValid()) {
            hideProgressDialog()
            myDialog.showDialog(this, "Alert", "Please fill in complete information.")
        } else {
            passValidator.setup()
            val data = ReqSignIn(email, binding.etPass.text.toString())
            viewModel.postSignIn(data)
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
