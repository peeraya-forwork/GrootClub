package com.example.grootclub.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Profile.ProfileRepository
import com.example.grootclub.databinding.FragmentProfileBinding
import com.example.grootclub.ui.profile.editProfile.ProfileEdit
import com.example.grootclub.ui.signIn.SignInViewModel
import com.example.grootclub.utils.loadImage
import com.example.grootclub.utils.sharedpreferences.SharedPreference
import com.example.grootclub.utils.sharedpreferences.SharedPreference.Companion.KEY_TOKEN

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private lateinit var sharedPref: SharedPreference
    private lateinit var viewModel: ProfileVM
    private lateinit var profileRepository: ProfileRepository

    override fun prepareView(savedInstanceState: Bundle?) {

        sharedPref = SharedPreference(requireContext())
        val token = sharedPref.getValueString(KEY_TOKEN)
        val serviceInstance = ApiService().getService(token)

        profileRepository = ProfileRepository(serviceInstance)
        viewModel = ViewModelProvider(this,ProfileVMFactory(profileRepository))[ProfileVM::class.java]

        initView()
        showProgressDialog()
        viewModel.postCurrentUser()
//        setDataUserSharedPreference()
        observeData()
        initToolBar()
        setOnClicks()
    }

    private fun observeData() {
        viewModel.currentUserResponse.observe(this) { result ->
            hideProgressDialog()
            if (result != null) {
                binding.imvProfile.loadImage(result.img)
                binding.tvName.text = result.fname + " " + result.lname

                binding.tvEmail.text = result.email
                binding.tvGender.text = result.gender
                binding.tvPhone.text = result.phone
                binding.tvBirtDay.text = result.birthday
                binding.tvAge.text = result.age
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initToolBar() {
        binding.toolbar.toolbarMain.visibility = View.GONE
    }

//    private fun setDataUserSharedPreference() {
//        sharedPref = SharedPreference(requireContext())
//        sharedPref.checkLogin()
//        val user: HashMap<String, String> = sharedPref.getUserDetails()
//
//        val name: String = user[SharedPreference.KEY_MAIL]!!
//
//        val token = sharedPref.getValueString(KEY_TOKEN)
//        Log.e("token", token.toString())
//
//        binding.tvName.text = name
//    }


    private fun initView() {

    }

    private fun setOnClicks() {
        binding.btnEditProfile.setOnClickListener {
            showProgressDialog()
            Intent(requireContext(), ProfileEdit::class.java).also {
                startActivity(it)
            }
        }

        binding.btnChangeInterest.setOnClickListener {
//            Toast.makeText(requireContext(), "Change Interest", Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), "อันนี้กะยังบ่ทันเฮ็ดจ้า พส.", Toast.LENGTH_SHORT)
                .show()

        }
    }

    override fun onResume() {
        super.onResume()
        hideProgressDialog()
    }
}