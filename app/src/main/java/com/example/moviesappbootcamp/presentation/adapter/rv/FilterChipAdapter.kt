package com.example.moviesappbootcamp.presentation.adapter.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.common.ChipFilter
import com.example.moviesappbootcamp.databinding.FragmentFilterBinding
import com.example.moviesappbootcamp.databinding.ItemFilterBinding

class FilterChipAdapter : RecyclerView.Adapter<FilterChipAdapter.FilterChipViewHolder>() {
    inner class FilterChipViewHolder (val itemFilterBinding: ItemFilterBinding) : RecyclerView.ViewHolder(itemFilterBinding.root)


    private var selectedChipPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterChipViewHolder {
        return FilterChipViewHolder(
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //
    override fun onBindViewHolder(holder: FilterChipViewHolder, position: Int) {
        val chip = differ.currentList[position]
        with(holder.itemFilterBinding){
            filter = chip
            layoutChip.setOnClickListener {
                if (!layoutChip.isChecked){
                    onChipSelected(-1)
                }else{
                    onChipSelected(position)
                }
            }
            layoutChip.isChecked = if(selectedChipPosition != -1) position == selectedChipPosition else false
            Log.e("FILTER","state : ${layoutChip.isChecked}, selected pos : $selectedChipPosition, current pos : $position")
        }
    }

    fun updateAdapter(newData : List<ChipFilter>){
        differ.submitList(newData)
    }

    private fun onChipSelected(position : Int){
        selectedChipPosition = position
    }
    fun reset(){
        selectedChipPosition = -1
    }

    private val differ = AsyncListDiffer(this,DiffCallbackBase<ChipFilter>())
}