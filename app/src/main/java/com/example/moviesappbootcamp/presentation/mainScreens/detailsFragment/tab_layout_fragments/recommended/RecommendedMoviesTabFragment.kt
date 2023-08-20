package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.tab_layout_fragments.recommended

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.common.utils.gone
import com.example.moviesappbootcamp.common.utils.visible
import com.example.moviesappbootcamp.databinding.FragmentRecommendedMoviesTabBinding
import com.example.moviesappbootcamp.presentation.UiState
import com.example.moviesappbootcamp.presentation.adapter.rv.MovieBigAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendedMoviesTabFragment (private val id : Int) : BaseFragment<FragmentRecommendedMoviesTabBinding>(FragmentRecommendedMoviesTabBinding::inflate) {

    private val viewModel by viewModels<RecommendedMoviesViewModel>()
    private val moviesAdapter = MovieBigAdapter()

    override fun onViewCreatedLight() {

        setRv()
        observe()
    }

    private fun observe(){
        val progressBar = binding.progressBar
        viewModel.getRecommendedMovies(id)
        with(viewModel){
            result.observe(viewLifecycleOwner){
                when(it){
                    is UiState.Loading -> progressBar.visible()
                    is UiState.Error -> {
                        progressBar.gone()
                        fancyToast(requireContext(),it.message,FancyToast.ERROR)
                    }
                    is UiState.Success -> {
                        progressBar.gone()
                        Log.e("success",it.data.size.toString())
                        moviesAdapter.differ.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun setRv(){
        val rv = binding.rvMovies
        //rv.layoutManager = object : GridLayoutManager(context,2) { override fun canScrollVertically() = false }
        rv.adapter = moviesAdapter
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}