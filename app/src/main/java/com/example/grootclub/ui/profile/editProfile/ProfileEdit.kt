package com.example.grootclub.ui.profile.editProfile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.appzaza.base.BaseActivity
import com.example.grootclub.R
import com.example.grootclub.components.dialog.MyDialog
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Profile.ProfileRepository
import com.example.grootclub.data.Request.Profile.ReqUpdateProfile
import com.example.grootclub.databinding.ProfileEditBinding
import com.example.grootclub.ui.profile.ProfileFragment
import com.example.grootclub.ui.profile.ProfileVM
import com.example.grootclub.ui.profile.ProfileVMFactory
import com.example.grootclub.ui.signIn.SignInActivity
import com.example.grootclub.utils.SafeClickListener
import com.example.grootclub.utils.loadImage
import com.example.grootclub.utils.setArrayAdapter
import com.example.grootclub.utils.sharedpreferences.SharedPreference

class ProfileEdit : BaseActivity<ProfileEditBinding>() {
    override val bindLayout: (LayoutInflater) -> ProfileEditBinding
        get() = ProfileEditBinding::inflate

    private lateinit var sharedPref: SharedPreference
    private lateinit var viewModel: ProfileVM
    private lateinit var profileRepository: ProfileRepository

    private var id: String? = null


    override fun prepareView(savedInstanceState: Bundle?) {

        sharedPref = SharedPreference(this)
        val token = sharedPref.getValueString(SharedPreference.KEY_TOKEN)
        id = sharedPref.getValueString(SharedPreference.KEY_ID)
        val serviceInstance = ApiService().getService(token)

        profileRepository = ProfileRepository(serviceInstance)
        viewModel = ViewModelProvider(this, ProfileVMFactory(profileRepository))[ProfileVM::class.java]
        viewModel.postCurrentUser()

        initToolbar()
        observeData()
        setOnClicks()
    }

    private fun observeData() {
        viewModel.currentUserResponse.observe(this) { result ->
            hideProgressDialog()
            if (result != null) {
                binding.imvProfile.loadImage(result.img)
                binding.etFName.setText(result.fname)
                binding.etLName.setText(result.lname)
                binding.etEmail.setText(result.email)
                binding.etBirthDay.text = result.birthday
                binding.tvAge.text = result.age

                val genderList = arrayOf("Gender", "Men", "Women", "Prefer not to say")
                val selectedGenderIndex = genderList.indexOf(result.gender)
                binding.spnGender.adapter = setArrayAdapter(this, genderList)
                binding.spnGender.setSelection(selectedGenderIndex)

                binding.etPhone.setText(result.phone)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.updateUserResponse.observe(this) { result ->
            hideProgressDialog()
            if (result != null) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
//                val myDialog = MyDialog()
//                myDialog.showDialogOKCallBack(this, "Alert", "SignUp Success") {
//                    val intent = Intent(this, SignInActivity::class.java)
//                    // ตั้งค่า flags เพื่อลบประวัติการเรียกของ SignInActivity
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                    startActivity(intent)
//                    finish()

//                    val data = "Hello from Activity!"
//                    val fragment = ProfileFragment.newInstance(data)
//
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment)
//                        .commit()
//                }
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.btnIconBack.setOnClickListener {
            finish()
        }
        binding.toolbar.toolbarTitle.text = "Edit Profile"
        binding.toolbar.toolbarTitle.visibility = View.VISIBLE
    }

    private fun setOnClicks() {
        binding.btnUpdate.setOnClickListener(SafeClickListener{
//            Toast.makeText(this, "Update Test Test", Toast.LENGTH_SHORT).show()
            showProgressDialog()
            checkInputData()
        })

        binding.cardImvProfile.setOnClickListener(SafeClickListener{
            getImageContent.launch("image/*")
        })
    }

    val getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.imvProfile.setImageURI(uri)
        }
    }

    private fun checkInputData() {
        val imvProfile = binding.imvProfile.drawable
        val fName = binding.etFName.text.toString()
        val lName = binding.etLName.text.toString()
        val gender = binding.spnGender.selectedItem.toString()
        val birthday = binding.etBirthDay.text.toString()
        val age = binding.tvAge.text.toString()
        val email = binding.etEmail.text.toString()
        val phone = binding.etPhone.text.toString()

        val data = ReqUpdateProfile(
            age,birthday,email,fName,gender,id,imvProfile.toString(),lName,phone
        )

        viewModel.updateUser(data)

    }

}