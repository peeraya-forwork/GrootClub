package com.example.grootclub.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.grootclub.data.ProductModel
import com.example.grootclub.databinding.ItemLayoutProductBinding
import com.example.grootclub.utils.loadImage

class HomeAdapter(
    private val products: ArrayList<ProductModel.Stadium>,
    private var onItemSelect: ((users: ProductModel.Stadium) -> Unit)? = null
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemLayoutProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentItem: ProductModel.Stadium? = null

        init {
            binding.btnReadMore.setOnClickListener {
                currentItem?.let { onItemSelect?.invoke(it) }
            }
        }

        fun bind(stadium: ProductModel.Stadium) {
            currentItem = stadium
            binding.tvSection.text = stadium.section
            binding.imv.loadImage(stadium.imv)
            binding.tvName.text = stadium.name
            binding.tvDetail.text = stadium.detail
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])

        //กดได้ทั้ง view
        holder.itemView.setOnClickListener {
            this.onItemSelect?.invoke(products[position])
        }
    }

    fun addData(stadium: List<ProductModel.Stadium>) {
        products.addAll(stadium)
        Log.e("MainAdapter", "addData:$stadium")
    }

    fun clearData() {
        products.clear()
        Log.e("MainAdapter", "clearData")
    }

    fun setOnItemSelect(onItemSelect: (stadium: ProductModel.Stadium) -> Unit) {
        this.onItemSelect = onItemSelect
    }

}