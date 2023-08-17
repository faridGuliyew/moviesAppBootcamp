package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.tab_layout_fragments.reviews

import android.os.Bundle
import android.util.Log
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
import com.example.moviesappbootcamp.databinding.FragmentReviewsBinding
import com.example.moviesappbootcamp.presentation.UiState
import com.example.moviesappbootcamp.presentation.adapter.rv.ReviewAdapter
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewsFragment (private val id : Int) : BaseFragment<FragmentReviewsBinding>(FragmentReviewsBinding::inflate) {

    private val viewModel by viewModels<ReviewsTabViewModel>()
    private val reviewAdapter = ReviewAdapter()


    override fun onViewCreatedLight() {
        setRv()
        observe()

    }

    private fun observe(){
        val progressBar = binding.progressBar
        viewModel.getReviews(id)
        with(viewModel){
            result.observe(viewLifecycleOwner){
                when(it){
                    is UiState.Loading -> progressBar.visible()
                    is UiState.Error -> {
                        progressBar.gone()
                        fancyToast(requireContext(),it.message, FancyToast.ERROR)
                    }
                    is UiState.Success -> {
                        progressBar.gone()
                        Log.e("success",it.data.size.toString())
                        reviewAdapter.differ.submitList(it.data)
                    }
                }
            }
        }
    }

    private fun setRv(){
        val rv = binding.reviewsRv
        rv.adapter = reviewAdapter
    }

}