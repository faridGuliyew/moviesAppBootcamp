package com.example.moviesappbootcamp.presentation.adapter.vp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.TopMovieViewPagerBinding
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel

class TopRatedViewPagerAdapter : RecyclerView.Adapter<TopRatedViewPagerAdapter.TopRatedViewHolder>() {
    inner class TopRatedViewHolder (private val topMovieViewPagerBinding: TopMovieViewPagerBinding) : RecyclerView.ViewHolder(topMovieViewPagerBinding.root){
        fun bind(movieModel : MovieBriefUiModel){
            with(topMovieViewPagerBinding){
                movie = movieModel
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedViewHolder {
        return TopRatedViewHolder(
            TopMovieViewPagerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TopRatedViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun updateAdapter(data : List<MovieBriefUiModel>){
        differ.submitList(data)
    }
    private val differ = AsyncListDiffer(this,DiffCallbackBase<MovieBriefUiModel>())
}