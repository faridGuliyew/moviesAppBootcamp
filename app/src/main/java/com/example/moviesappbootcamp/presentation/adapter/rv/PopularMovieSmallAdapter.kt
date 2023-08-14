package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.ItemMovieSmallBinding
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel

class PopularMovieSmallAdapter : RecyclerView.Adapter<PopularMovieSmallAdapter.MovieSmallViewHolder>() {
    inner class MovieSmallViewHolder (private val itemMovieSmallBinding: ItemMovieSmallBinding) : RecyclerView.ViewHolder(itemMovieSmallBinding.root){
        fun bind(movieModel : MovieLayoutModel){
            with(itemMovieSmallBinding){
                movie = movieModel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSmallViewHolder {
        return MovieSmallViewHolder(
            ItemMovieSmallBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieSmallViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
    fun updateAdapter(newData : List<MovieLayoutModel>){
        differ.submitList(newData)
    }

    private val differ = AsyncListDiffer(this,DiffCallbackBase<MovieLayoutModel>())
}