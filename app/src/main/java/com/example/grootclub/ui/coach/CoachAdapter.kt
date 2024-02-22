package com.example.grootclub.ui.coach

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grootclub.R
import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.databinding.ItemLayoutCoachBinding
import com.example.grootclub.utils.loadImage

class CoachAdapter(
    private val coaches: ArrayList<CoachListModelItem>,
    private var onItemSelect: ((coach: CoachListModelItem) -> Unit)? = null,
    private val coachItemClickReadMore: CoachItemClickReadMore? = null
) : RecyclerView.Adapter<CoachAdapter.ViewHolder>() {
    var coachItemClickListener: CoachFragment.CoachItemClickListener? = null
    interface CoachItemClickReadMore {
        fun onReadMoreClicked(coach: CoachListModelItem)
        fun onItemClicked(coach: CoachListModelItem)
    }

    inner class CustomDiffCallback(
        private val oldList: List<CoachListModelItem>,
        private val newList: List<CoachListModelItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]._id == newList[newItemPosition]._id
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


    inner class ViewHolder(private val binding: ItemLayoutCoachBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentCoach: CoachListModelItem? = null
        private var isOpen: Boolean = false

        init {
            // กดทั้ง View
            itemView.setOnClickListener {
                currentCoach?.let {
//                    Toast.makeText(itemView.context, "View Clicked", Toast.LENGTH_SHORT).show()
                    onItemSelect?.invoke(it)
//                    coachItemClickReadMore?.onItemClicked(it)
//                    coachItemClickReadMore?.onReadMoreClicked(it)
                }
            }

            // กดที่ btnReadMore
            binding.btnReadMore.setOnClickListener {
                currentCoach?.let {
//                    Toast.makeText(itemView.context, "Read More Clicked", Toast.LENGTH_SHORT).show()
                    isOpen = !isOpen
//                    onItemSelect?.invoke(it)
                    updateCardDetailVisibility()
                    coachItemClickReadMore?.onReadMoreClicked(it)
                }
            }
        }

        private fun updateCardDetailVisibility() {
            if (isOpen) {
                binding.cardDetail.visibility = View.VISIBLE
                binding.btnReadMore.setBackgroundResource(R.drawable.arrow_up)
            } else {
                binding.cardDetail.visibility = View.GONE
                binding.btnReadMore.setBackgroundResource(R.drawable.arrow_down)
            }
        }

        fun bind(coach: CoachListModelItem) {
            currentCoach = coach
            binding.tvNameCoach.text = coach.name
            binding.imv.loadImage(coach.image)
            binding.tvNameSpot.text = coach.type
            binding.tvDetail.text = coach.des
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutCoachBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = coaches.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coaches[position])

        //กดได้ทั้ง view
        holder.itemView.setOnClickListener {
            Log.d("CoachAdapter", "itemView : ${coaches[position].name}")
//            this.onItemSelect?.invoke(coaches[position])
            this.coachItemClickReadMore?.onItemClicked(coaches[position])
        }
    }

    fun addData(newData: List<CoachListModelItem>) {
        val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(coaches, newData))
        coaches.clear()
        coaches.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(coaches, emptyList()))
        coaches.clear()
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemSelect(onItemSelect: (coach: CoachListModelItem) -> Unit) {
        this.onItemSelect = onItemSelect
    }

}