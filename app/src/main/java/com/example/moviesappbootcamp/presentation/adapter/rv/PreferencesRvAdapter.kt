package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.common.model.other.PreferencesModel
import com.example.moviesappbootcamp.databinding.ItemPreferencesBinding

class PreferencesRvAdapter : RecyclerView.Adapter<PreferencesRvAdapter.PreferencesViewHolder>() {
    inner class PreferencesViewHolder (private val itemPreferencesBinding: ItemPreferencesBinding) : RecyclerView.ViewHolder(itemPreferencesBinding.root){
        fun bind(preferenceModel : PreferencesModel){
            with(itemPreferencesBinding){
                preference = preferenceModel
                buttonIcon.setOnClickListener {
                    onItemClick()
                }
            }
        }
    }

    private var onItemClick = fun(){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferencesViewHolder {
        return PreferencesViewHolder(
            ItemPreferencesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PreferencesViewHolder, position: Int) {
       holder.bind(differ.currentList[position])
    }

    fun setOnItemClickListener(listener : ()->Unit){
        onItemClick = listener
    }

    val differ = AsyncListDiffer(this, DiffCallbackBase<PreferencesModel>())
}