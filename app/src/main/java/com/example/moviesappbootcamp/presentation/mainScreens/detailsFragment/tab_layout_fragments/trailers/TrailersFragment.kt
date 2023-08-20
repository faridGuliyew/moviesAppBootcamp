package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.tab_layout_fragments.trailers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.common.utils.gone
import com.example.moviesappbootcamp.common.utils.visible
import com.example.moviesappbootcamp.databinding.FragmentTrailersBinding
import com.example.moviesappbootcamp.presentation.UiState
import com.example.moviesappbootcamp.presentation.adapter.rv.TrailersRvAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrailersFragment (private val id : Int) : BaseFragment<FragmentTrailersBinding>(FragmentTrailersBinding::inflate) {

    private val viewModel by viewModels<TrailersViewModel>()
    private val adapter by lazy {
        TrailersRvAdapter(lifecycle)
    }
    override fun onViewCreatedLight() {

        setRv()
        observe()
    }

    private fun observe(){
        with(viewModel){
            getVideos(id)
            state.observe(viewLifecycleOwner){
                when(it){
                    is UiState.Loading ->{
                        binding.progressBar2.visible()
                    }
                    is UiState.Error-> {
                        binding.progressBar2.gone()
                        fancyToast(requireContext(),it.message,FancyToast.ERROR)
                    }
                    is UiState.Success-> {
                        binding.progressBar2.gone()
                        adapter.differ.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun setRv(){
        binding.trailersRv.adapter = adapter
        adapter.setOnYoutubeButtonClickListener { key->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
            intent.putExtra("force_fullscreen", true)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}