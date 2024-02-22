package com.example.grootclub.ui.signup

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.grootclub.R
import com.example.grootclub.components.dialog.MyDialog
import com.example.grootclub.databinding.ActivitySignUpBinding
import com.example.grootclub.ui.signIn.SignInActivity
import com.example.grootclub.utils.EditTextValidator
import com.example.grootclub.utils.SafeClickListener
import com.example.grootclub.utils.Utils
import com.example.grootclub.utils.initDatePickerCurrentDate20
import com.example.grootclub.utils.loadImage
import com.example.grootclub.utils.setArrayAdapter

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

// Register ActivityResult handler
        val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
                // Handle permission requests results
                // See the permission example in the Android platform samples: https://github.com/android/platform-samples
            handlePermissionResults(results)
        }

        // Permission request logic
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermissions.launch(
                arrayOf(
                    READ_MEDIA_IMAGES,
                    READ_MEDIA_VIDEO,
                    READ_MEDIA_VISUAL_USER_SELECTED
                )
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VIDEO))
        } else {
            requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
        checkMediaPermission()
        initView()
        initToolbar()
        setOnClicks()
    }

    private fun handlePermissionResults(results: Map<String, Boolean>) {
        val grantedPermissions = results.filterValues { it }.keys
        val deniedPermissions = results.filterValues { !it }.keys

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            (READ_MEDIA_IMAGES in grantedPermissions || READ_MEDIA_VIDEO in grantedPermissions)
        ) {
            // Full access on Android 13 (API level 33) or higher

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            READ_MEDIA_VISUAL_USER_SELECTED in grantedPermissions
        ) {
            // Partial access on Android 14 (API level 34) or higher
        } else if (READ_EXTERNAL_STORAGE in grantedPermissions) {
            // Full access up to Android 12 (API level 32)
        } else {
            // Access denied for all requested permissions
        }
    }

    private fun initView() {
        val genderList = arrayOf("Gender", "Men", "Women", "Prefer not to say")
        binding.spnGender.adapter = setArrayAdapter(this, genderList)

        binding.spnGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                Toast.makeText(this@SignUpActivity, genderList[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@SignUpActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }

        }

        binding.etBirthDay.setOnClickListener(SafeClickListener{
            initDatePickerCurrentDate20(this, binding.etBirthDay, binding.tvAge)
        })

        binding.cardImvProfile.setOnClickListener(SafeClickListener{
            getImageContent.launch("image/*")
        })
    }

    private fun initToolbar() {
        binding.toolbar.btnIconBack.setOnClickListener {
            finish()
        }
    }

    private fun setOnClicks() {
        binding.btnSignUp.setOnClickListener(SafeClickListener{
            checkInputData()
        })
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
        val imvProfile = binding.imvProfile.drawable
        val userName = binding.etUserName.text.toString()
        val password = binding.etPass.text.toString()
        val confirmPassword = binding.etConfirmPass.text.toString()
        val passwordValidator = EditTextValidator(binding.etConfirmPass)
        val confirmPasswordValidator = EditTextValidator(binding.etConfirmPass)
        val fName = binding.etFName.text.toString()
        val lName = binding.etLName.text.toString()
        val gender = binding.spnGender.selectedItem.toString()
        val birthday = binding.etBirthDay.text.toString()
        val age = binding.tvAge.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()

        if (userName.isNotEmpty() && passwordValidator.isValid() && confirmPasswordValidator.isValid() &&
            fName.isNotEmpty() && lName.isNotEmpty() && gender.isNotEmpty() && birthday.isNotEmpty() &&
            age.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
            if (imvProfile == null) {
                myDialog.showDialog(this, "Alert", "Please select profile image.")
            } else if (!Utils.isValidEmail(binding.etEmail)) {
                myDialog.showDialog(this, "Alert", "Please fill in correct email information.")
            } else if (password == confirmPassword) {
                myDialog.showDialogOKCallBack(this, "Alert", "SignUp Success") {
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
    private fun checkMediaPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            (ContextCompat.checkSelfPermission(
                this,
                READ_MEDIA_IMAGES
            ) == PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                READ_MEDIA_VIDEO
            ) == PERMISSION_GRANTED)) {
            // Full access on Android 13 (API level 33) or higher
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            ContextCompat.checkSelfPermission(
                this,
                READ_MEDIA_VISUAL_USER_SELECTED
            ) == PERMISSION_GRANTED) {
            // Partial access on Android 14 (API level 34) or higher
        } else if (ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            ) == PERMISSION_GRANTED) {
            // Full access up to Android 12 (API level 32)
        } else {
            // Access denied
            Toast.makeText(this, "Access denied", Toast.LENGTH_SHORT).show()
        }
    }

    val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.imvProfile.setImageURI(uri)
        }
    }
}