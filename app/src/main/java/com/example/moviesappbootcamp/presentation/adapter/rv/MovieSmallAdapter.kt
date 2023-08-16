package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.ItemMovieSmallBinding
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel

class MovieSmallAdapter : RecyclerView.Adapter<MovieSmallAdapter.MovieSmallViewHolder>() {
    inner class MovieSmallViewHolder (private val itemMovieSmallBinding: ItemMovieSmallBinding) : RecyclerView.ViewHolder(itemMovieSmallBinding.root){
        fun bind(movieModel : MovieBriefUiModel){
            with(itemMovieSmallBinding){
                movie = movieModel
                main.setOnClickListener {
                    onClickEvent(movieModel.movieId)
                }
            }
        }
    }

    private var onClickEvent = fun(_: Int){}

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

    fun setOnClickEvent(onClickLambdaFunction : (movieId : Int)->Unit){
        onClickEvent = onClickLambdaFunction
    }
    fun updateAdapter(newData : List<MovieBriefUiModel>){
        differ.submitList(newData)
    }

    private val differ = AsyncListDiffer(this,DiffCallbackBase<MovieBriefUiModel>())
}