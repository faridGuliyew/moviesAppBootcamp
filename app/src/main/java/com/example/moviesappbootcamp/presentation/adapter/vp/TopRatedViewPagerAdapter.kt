package com.example.moviesappbootcamp.presentation.adapter.vp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.databinding.TopMovieViewPagerBinding
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel

class TopRatedViewPagerAdapter : RecyclerView.Adapter<TopRatedViewPagerAdapter.TopRatedViewHolder>() {
    inner class TopRatedViewHolder (private val topMovieViewPagerBinding: TopMovieViewPagerBinding) : RecyclerView.ViewHolder(topMovieViewPagerBinding.root){
        fun bind(movieModel : MovieLayoutModel){
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

    fun updateAdapter(data : List<MovieLayoutModel>){
        differ.submitList(data)
    }


    private val diffItemCallback = object : DiffUtil.ItemCallback<MovieLayoutModel>(){
        override fun areItemsTheSame(
            oldItem: MovieLayoutModel,
            newItem: MovieLayoutModel,
        ): Boolean {
            return oldItem.movieName == newItem.movieName
        }

        override fun areContentsTheSame(
            oldItem: MovieLayoutModel,
            newItem: MovieLayoutModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this,diffItemCallback)
}