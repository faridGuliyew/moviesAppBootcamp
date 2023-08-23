package com.example.moviesappbootcamp.presentation.adapter.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.base.diffUtil.DiffCallbackBase
import com.example.moviesappbootcamp.databinding.ItemTrailerBinding
import com.example.moviesappbootcamp.domain.model.TrailerUiModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrailersRvAdapter (private val lifecycle: Lifecycle) : RecyclerView.Adapter<TrailersRvAdapter.TrailersViewHolder>() {
    inner class TrailersViewHolder (private val itemTrailerBinding: ItemTrailerBinding) : RecyclerView.ViewHolder(itemTrailerBinding.root){
        fun bind(trailerModel : TrailerUiModel){
            with(itemTrailerBinding){
                trailer = trailerModel
                lifecycle.addObserver(youTubePlayerView)
                val listener = object : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.cueVideo(trailerModel.key,0f)
                    }
                }
                val iFrame = IFramePlayerOptions.Builder().controls(1)
                    .fullscreen(1)
                    .build()

                try { youTubePlayerView.initialize(listener,iFrame) } catch (e:Exception){}
                buttonYt.setOnClickListener { onYoutubeButtonClick(trailerModel.key) }
            }
        }
    }

    private var onYoutubeButtonClick = fun(key : String){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersViewHolder {
        return TrailersViewHolder(
            ItemTrailerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TrailersViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setOnYoutubeButtonClickListener(listener : (key : String)->Unit){
        onYoutubeButtonClick = listener
    }

    val differ = AsyncListDiffer(this, DiffCallbackBase<TrailerUiModel>())
}