package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.databinding.FragmentDetailsBinding
import com.example.moviesappbootcamp.domain.model.CreditsUiModel
import com.example.moviesappbootcamp.domain.model.MovieDetailedUiModel
import com.example.moviesappbootcamp.presentation.adapter.rv.MovieCreditsAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()
    private val creditsAdapter = MovieCreditsAdapter()

    override fun onViewCreatedLight() {

        setCreditsRv()
        observe()
    }

    private fun observe(){
        viewModel.getSingleMovie(args.movieId)
        val loadingDialog = CustomLoadingDialog(requireContext(),layoutInflater,"Please wait...")
        with(viewModel){
            uiState.observe(viewLifecycleOwner){
                when(it){
                    is DetailsUiState.Loading->loadingDialog.setDialogText(it.message)
                    is DetailsUiState.Error->{
                        loadingDialog.dismiss()
                        fancyToast(requireContext(),it.message,FancyToast.ERROR)
                    }
                    is DetailsUiState.Unavailable->{
                        loadingDialog.dismiss()
                        fancyToast(requireContext(),it.message,FancyToast.INFO)
                    }
                    is DetailsUiState.Success->{
                        loadingDialog.dismiss()
                        if (it.type == DetailsDataType.MOVIE_DETAILS){
                            binding.movie = it.data as MovieDetailedUiModel
                        }else{
                            creditsAdapter.differ.submitList(it.data as List<CreditsUiModel>)
                        }
                    }
                }
            }
        }
    }

    private fun setCreditsRv(){
        val rv = binding.rvPeople
        rv.adapter = creditsAdapter
    }

}