package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.common.utils.gone
import com.example.moviesappbootcamp.common.utils.visible
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
        setMotionAnimation()
        observe()
    }

    private fun observe() {
        viewModel.getSingleMovie(args.movieId)
        val loadingDialog = CustomLoadingDialog(requireContext(), layoutInflater, "Please wait...")
        with(viewModel) {
            uiState.observe(viewLifecycleOwner) {
                when (it) {
                    is DetailsUiState.Loading -> loadingDialog.setDialogText(it.message)
                    is DetailsUiState.Error -> {
                        loadingDialog.dismiss()
                        fancyToast(requireContext(), it.message, FancyToast.ERROR)
                    }

                    is DetailsUiState.Unavailable -> {
                        loadingDialog.dismiss()
                        fancyToast(requireContext(), it.message, FancyToast.INFO)
                    }

                    is DetailsUiState.SuccessCredits -> {
                        loadingDialog.dismiss()
                        creditsAdapter.differ.submitList(it.data as List<CreditsUiModel>)
                    }

                    is DetailsUiState.SuccessDetails -> {
                        loadingDialog.dismiss()
                        binding.movie = it.data as MovieDetailedUiModel
                        genresAdapter.updateAdapter(it.data.movieGenres)
                        showRating(it.data)
                    }
                }
            }
        }
    }

    private fun setCreditsRv() {
        val rv = binding.rvPeople
        rv.adapter = creditsAdapter
    }

    private fun setGenresRv() {
        val rv = binding.genresRv
        rv.adapter = genresAdapter
    }

    private fun setViewPager() {
        val viewPager = binding.tabViewPager
        viewPager.adapter =
            DetailsTabViewPagerAdapter(childFragmentManager, lifecycle, args.movieId)
        val tabTitles = listOf("More like this", "Trailers", "Comments")
        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setMotionAnimation() {
        var justSwitched = 0
        with(binding.imageViewBackdrop) {
            binding.scrollView.viewTreeObserver.addOnScrollChangedListener {
                val movement = binding.scrollView.scrollY.toFloat()
                if (movement == 0f && justSwitched >= 6) {
                    visible()
                } else if (movement != 0f && justSwitched >= 10) {
                    gone()
                    justSwitched = 1
                }
                justSwitched++
            }
        }
    }

    private fun showRating(movie: MovieDetailedUiModel) {
        binding.buttonRating.setOnClickListener {
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToGiveRatingFragment(
                    movie
                )
            )
        }
    }

    private fun share() {
        binding.buttonShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            startActivity(intent)
        }
    }
}