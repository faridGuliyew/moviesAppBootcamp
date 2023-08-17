package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment

import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.ChipFilter
import com.example.moviesappbootcamp.common.filter.GenreFilter
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.databinding.FragmentDetailsBinding
import com.example.moviesappbootcamp.domain.model.CreditsUiModel
import com.example.moviesappbootcamp.domain.model.MovieDetailedUiModel
import com.example.moviesappbootcamp.presentation.adapter.rv.FilterChipAdapter
import com.example.moviesappbootcamp.presentation.adapter.rv.MovieCreditsAdapter
import com.example.moviesappbootcamp.presentation.adapter.vp.DetailsTabViewPagerAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel by viewModels<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()
    private val creditsAdapter = MovieCreditsAdapter()
    private val genresAdapter = FilterChipAdapter()

    override fun onViewCreatedLight() {

        setCreditsRv()
        setGenresRv()
        setViewPager()
        share()
        //setMotionAnimation()
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
                            //todo
                            genresAdapter.updateAdapter(it.data.movieGenres.map { genre-> ChipFilter.Genre(GenreFilter.findGenreById(genre.id)) })
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

    private fun setGenresRv(){
        val rv = binding.genresRv
        rv.adapter = genresAdapter
    }

    private fun setViewPager(){
        val viewPager = binding.tabViewPager
        viewPager.adapter = DetailsTabViewPagerAdapter(childFragmentManager, lifecycle, args.movieId)
        val tabTitles = listOf("More like this", "Trailers", "Comments")
        TabLayoutMediator(binding.tabLayout,viewPager){ tab, position->
            tab.text = tabTitles[position]
        }.attach()
    }

    //todo
    private fun setMotionAnimation() {
        val image = binding.imageViewBackdrop
        val scrollView = binding.scrollView
        val container = binding.container
        val initialChildCount = container.childCount
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val scrollHeight = scrollView.height
            val movement = scrollView.scrollY.toFloat()
            if (movement >= scrollHeight / 2 && container.childCount == initialChildCount){
                container.removeView(image)
            }
            if (movement == 0f && container.childCount != initialChildCount){
                container.addView(image)
            }
        }
    }

    private fun share(){
        binding.buttonShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            startActivity(intent)
        }
    }
}