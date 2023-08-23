package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.ItemMovieBigBinding
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel

class MovieBigAdapter : RecyclerView.Adapter<MovieBigAdapter.MovieViewHolder>(){
    inner class MovieViewHolder(private val itemMovieBigBinding: ItemMovieBigBinding) : RecyclerView.ViewHolder(itemMovieBigBinding.root){
        fun bind(movieModel : MovieBriefUiModel){
            with(itemMovieBigBinding){
                movie = movieModel
                main.setOnClickListener {
                    onClickEvent(movieModel.movieId)
                }
            }
        }
    }
    private var onClickEvent = fun(_: Int){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBigBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnClickEvent(onClickLambdaFunction : (movieId : Int)->Unit){
        onClickEvent = onClickLambdaFunction
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    val differ = AsyncListDiffer(this, DiffCallbackBase<MovieBriefUiModel>())
}