package com.example.grootclub.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.grootclub.data.ProductModel
import com.example.grootclub.databinding.ActivityDetailBinding
import com.example.grootclub.utils.Utils.parcelableArrayList

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        val receivedIntent = intent
        if (receivedIntent != null && receivedIntent.hasExtra("stadium_data")) {
            val stadium = receivedIntent.getParcelableExtra<ProductModel.Stadium>("stadium_data")

            if (stadium != null) {
                // คุณสามารถใช้ข้อมูลในตัวแปร 'stadium' ใน activity ปลายทาง
                val stadiumName = stadium.name
                val stadiumSection = stadium.section
                binding.itemCardView.tvNameStadium.text = stadiumName
                binding.tvSection.text = stadiumSection
                Log.e("DetailActivity", "stadium: ${stadium.name} ${stadium.section}")
            }
        }

//        initView()
        setOnClicks()
        setContentView(binding.root)
    }

    private fun setOnClicks() {
        binding.itemCardView.cardStadium.setOnClickListener {
            Toast.makeText(this, "ยังบ่ทันเฮ็ดจ้า พส.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        val stadium = intent.parcelableArrayList<ProductModel.Stadium>("stadium")

        if (stadium != null) {
            // ทำสิ่งที่คุณต้องการกับข้อมูล Stadium ที่ส่งมาจาก HomeFragment
            Toast.makeText(this, "ยังบ่ทันเฮ็ดจ้า พส. $stadium", Toast.LENGTH_SHORT).show()

        }

    }
}