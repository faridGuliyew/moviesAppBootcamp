package com.example.moviesappbootcamp.presentation.adapter.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.ItemMovieSearchBinding
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel

class SearchPagingAdapter : PagingDataAdapter<MovieBriefUiModel, SearchPagingAdapter.SearchPagingViewHolder>(MyDiffCallback) {
    inner class SearchPagingViewHolder (private val itemMovieSearchBinding: ItemMovieSearchBinding) : RecyclerView.ViewHolder(itemMovieSearchBinding.root){
        fun bind(movieBriefUiModel: MovieBriefUiModel){
            with(itemMovieSearchBinding){
                movie = movieBriefUiModel
                main.setOnClickListener {
                    onClickEvent(movieBriefUiModel.movieId)
                }
            }
        }
    }

    private var onClickEvent = fun(_: Int){}

    override fun onBindViewHolder(holder: SearchPagingViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    fun setOnClickEvent(onClickLambdaFunction : (movieId : Int)->Unit){
        onClickEvent = onClickLambdaFunction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPagingViewHolder {
        return SearchPagingViewHolder(
            ItemMovieSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    companion object{
        val MyDiffCallback = DiffCallbackBase<MovieBriefUiModel>()
    }
}