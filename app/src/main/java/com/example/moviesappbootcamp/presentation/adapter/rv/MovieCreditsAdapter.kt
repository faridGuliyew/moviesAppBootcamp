package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.ItemCreditsBinding
import com.example.moviesappbootcamp.domain.model.CreditsUiModel

class MovieCreditsAdapter: RecyclerView.Adapter<MovieCreditsAdapter.MovieCreditsViewHolder>() {
    inner class MovieCreditsViewHolder (private val itemCreditsBinding: ItemCreditsBinding) : RecyclerView.ViewHolder(itemCreditsBinding.root){
        fun bind(personModel : CreditsUiModel){
            with(itemCreditsBinding){
                person = personModel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCreditsViewHolder {
        return MovieCreditsViewHolder(
            ItemCreditsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieCreditsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    val differ = AsyncListDiffer(this, DiffCallbackBase<CreditsUiModel>())
}