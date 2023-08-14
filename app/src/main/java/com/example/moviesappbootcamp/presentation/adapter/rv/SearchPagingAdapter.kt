package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.ItemMovieSearchBinding
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel

class SearchPagingAdapter : PagingDataAdapter<MovieLayoutModel, SearchPagingAdapter.SearchPagingViewHolder>(MyDiffCallback) {
    inner class SearchPagingViewHolder (private val itemMovieSearchBinding: ItemMovieSearchBinding) : RecyclerView.ViewHolder(itemMovieSearchBinding.root){
        fun bind(movieLayoutModel: MovieLayoutModel){
            with(itemMovieSearchBinding){
                movie = movieLayoutModel
            }
        }
    }

    override fun onBindViewHolder(holder: SearchPagingViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPagingViewHolder {
        return SearchPagingViewHolder(
            ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    companion object{
        val MyDiffCallback = DiffCallbackBase<MovieLayoutModel>()
    }
}