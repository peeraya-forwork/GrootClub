package com.example.grootclub.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.grootclub.databinding.FragmentProfileBinding
import com.example.grootclub.utils.sharedpreferences.SharedPreference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentProfileBinding? = null
    private lateinit var sharedPref: SharedPreference


    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        initView()
        setDataUserSharedPreference()
        initToolBar()
        setOnClicks()
        return binding.root
    }

    private fun initToolBar() {
        binding.toolbar.toolbarMain.visibility = View.GONE
    }

    private fun setDataUserSharedPreference() {
        sharedPref = SharedPreference(requireContext())
        sharedPref.checkLogin()
        val user: HashMap<String, String> = sharedPref.getUserDetails()

        val name: String = user[SharedPreference.KEY_MAIL]!!

        binding.tvName.text = name
//        binding.password.text ="Pass: $pass"
    }


    private fun initView() {

    }

    private fun setOnClicks() {
        binding.btnEditProfile.setOnClickListener {
//            Toast.makeText(requireContext(), "Edit Profile", Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), "ยังบ่ทันเฮ็ดจ้า พส.", Toast.LENGTH_SHORT).show()
        }

        binding.btnChangeInterest.setOnClickListener {
//            Toast.makeText(requireContext(), "Change Interest", Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), "อันนี้กะยังบ่ทันเฮ็ดจ้า พส.", Toast.LENGTH_SHORT).show()

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}