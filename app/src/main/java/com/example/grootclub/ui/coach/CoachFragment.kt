package com.example.grootclub.ui.coach

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grootclub.MainActivity
import com.example.grootclub.SharedViewModel
import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.databinding.FragmentCoachsBinding

class CoachFragment : Fragment() {

    private var _binding: FragmentCoachsBinding? = null
    private var adapter = CoachAdapter(arrayListOf())
    private val binding get() = _binding!!
    private var coachItemClickListener: CoachItemClickListener? = null
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: CoachVM
    private lateinit var repository: CoachRepository

    interface CoachItemClickListener {
        fun onItemClicked(item: CoachListModelItem)
        fun onReadMoreClicked(item: CoachListModelItem)
    }

    // สร้างเมทอด setHomeItemClickListener เพื่อให้คุณสามารถตั้งค่า Callback จากหน้า Main Activity
    fun setCoachItemClickListener(listener: CoachItemClickListener) {
        coachItemClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        repository = CoachRepository(ApiService().getService())
        viewModel = ViewModelProvider(this, CoachVMFactory(repository))[CoachVM::class.java]

        _binding = FragmentCoachsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.fetchAllCoach()
        adapter.clearData()
        initListView()

        return root
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private fun initListView() {
//        binding.progress.visibility = View.VISIBLE
//
//        // เปลี่ยนตำแหน่งการกำหนด adapter ไปหลังจากที่ observe ข้อมูลแล้ว
//        viewModel.coachList.observe(viewLifecycleOwner, Observer { coachList ->
//            binding.rcv.layoutManager = LinearLayoutManager(requireContext())
//
//            // ไม่ต้องกำหนด adapter อีกทีที่นี่
//            // binding.rcv.adapter = adapter
//
//            // ทำการ set ค่าให้กับ adapter ที่ได้ถูกสร้างในครั้งแรกแล้ว
//            adapter.addData(coachList)
//            adapter.notifyDataSetChanged()
//            binding.progress.visibility = View.GONE
//
//            // และ set ค่าให้กับคุณลักษณะของ CoachItemClickListener
//            adapter.coachItemClickListener = coachItemClickListener
//        })
//
//        // ไม่ต้องสร้าง adapter ใหม่ที่นี่
//        // adapter = CoachAdapter(arrayListOf(), onItemSelect = { _ -> }, coachItemClickReadMore = object : CoachAdapter.CoachItemClickReadMore {
//        //     override fun onReadMoreClicked(coach: CoachListModelItem) {
//        //         Toast.makeText(context, "ReadMore", Toast.LENGTH_SHORT).show()
//        //     }
//        // })
//    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListView() {
        binding.progress.visibility = View.VISIBLE
        viewModel.coachList.observe(viewLifecycleOwner, Observer { coachList ->
            binding.rcv.layoutManager = LinearLayoutManager(requireContext())
            binding.rcv.adapter = adapter
            adapter.addData(coachList)
            adapter.notifyDataSetChanged()
            binding.progress.visibility = View.GONE

//            adapter.setOnItemSelect { coach ->
//                sharedViewModel.selectCoach(coach)
//            }
            adapter.coachItemClickListener = coachItemClickListener

        })

//        adapter = CoachAdapter(arrayListOf(), onItemSelect = { _ ->
////            sharedViewModel.selectCoach(coach)
//        }, coachItemClickReadMore = object : CoachAdapter.CoachItemClickReadMore {
//            override fun onReadMoreClicked(coach: CoachListModelItem) {
//                Toast.makeText(context, "ReadMore", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onItemClicked(coach: CoachListModelItem) {
//                Toast.makeText(context, "View Clicked", Toast.LENGTH_SHORT).show()
//
//            }
//        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
