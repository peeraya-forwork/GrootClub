package com.example.grootclub.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grootclub.SharedViewModel
import com.example.grootclub.data.ProductModel
import com.example.grootclub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapter = HomeAdapter(arrayListOf())
    private val binding get() = _binding!!
    private var homeItemClickListener: HomeItemClickListener? = null
    private lateinit var sharedViewModel: SharedViewModel

    interface HomeItemClickListener {
        fun onItemClicked(item: ProductModel.Stadium)
    }

    // สร้างเมทอด setHomeItemClickListener เพื่อให้คุณสามารถตั้งค่า Callback จากหน้า Main Activity
    fun setHomeItemClickListener(listener: HomeItemClickListener) {
        homeItemClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        adapter.clearData()
        mockData()
        initListView()

        return root

    }

    private fun initListView() {
        binding.rcv.layoutManager = LinearLayoutManager(requireContext())
//        binding.rcv.run { //ขีดเส้นแต่ละรายการ
//            addItemDecoration(
//                DividerItemDecoration(
//                    binding.rcv.context,
//                    (binding.rcv.layoutManager as LinearLayoutManager).orientation
//                )
//            )
//        }
        binding.rcv.adapter = adapter
        adapter.setOnItemSelect { stadium ->
            // ใช้ viewModel เพือ callback ไปหน้า Main Activity
            sharedViewModel.selectStadium(stadium)
        }
    }

//    private fun onItemClick(item: ProductModel.Stadium) {
//        homeItemClickListener?.onItemClicked(item)
////        sharedViewModel.selectStadium(item)
////        Toast.makeText(requireContext(), item.name, Toast.LENGTH_SHORT).show()
//    }


    @SuppressLint("NotifyDataSetChanged")
    private fun mockData() {
        // Mock ข้อมูลสำหรับ RecyclerView
        val stadiumList = listOf(
            ProductModel.Stadium(1, "Section 1", "image1", "Stadium 1", "Stadium 1 Detail"),
            ProductModel.Stadium(2, "Section 2", "image2", "Stadium 2", "Stadium 2 Detail"),
            ProductModel.Stadium(3, "Section 3", "image3", "Stadium 3", "Stadium 3 Detail"),
            ProductModel.Stadium(4, "Section 4", "image4", "Stadium 4", "Stadium 4 Detail"),
            ProductModel.Stadium(5, "Section 5", "image5", "Stadium 5", "Stadium 5 Detail"),
            ProductModel.Stadium(6, "Section 6", "image6", "Stadium 6", "Stadium 6 Detail")
        )

        // เพิ่มข้อมูลลงใน RecyclerView Adapter
        adapter.addData(stadiumList)

        // แจ้งให้ RecyclerView ทราบว่าข้อมูลเปลี่ยนแปลงและต้องทำการอัปเดต
        adapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}