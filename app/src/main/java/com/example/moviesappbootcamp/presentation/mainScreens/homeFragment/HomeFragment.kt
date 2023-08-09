package com.example.moviesappbootcamp.presentation.mainScreens.homeFragment

import android.app.ActionBar
import android.content.res.Resources
import android.graphics.Movie
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.databinding.FragmentHomeBinding
import com.example.moviesappbootcamp.presentation.adapter.rv.PopularMovieSmallAdapter
import com.example.moviesappbootcamp.presentation.adapter.vp.TopRatedViewPagerAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.example.moviesappbootcamp.utils.fancyToast
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.Float

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val viewPagerAdapter = TopRatedViewPagerAdapter()
    private val topTenAdapter = PopularMovieSmallAdapter()
    private val newReleasesAdapter = PopularMovieSmallAdapter()
    private val recommendedAdapter = PopularMovieSmallAdapter()

    override fun onViewCreatedLight() {
        observe()
        setViewPager()
        setRv()
        setMotionAnimation()
        goToExplore()
    }

    private fun observe() {
        val loadingDialog =
            CustomLoadingDialog(requireContext(), layoutInflater, "Please wait...", true)
        with(viewModel) {
            result.onEach {
                when (it) {
                    is Resource.Loading -> {
                        loadingDialog.show()
                    }

                    is Resource.Error -> {
                        loadingDialog.dismiss()
                        fancyToast(requireContext(), it.message!!, FancyToast.ERROR)
                    }

                    is Resource.Success -> {
                        loadingDialog.dismiss()
                        val movieModels = it.data!!.movieLayoutModels
                        when(it.data.movieType){
                            is MovieType.POPULAR->{
                                viewPagerAdapter.updateAdapter(movieModels)
                            }
                            is MovieType.TOP_RATED->{
                                topTenAdapter.updateAdapter(movieModels.subList(0,10))
                            }
                            is MovieType.RECENT->{
                                newReleasesAdapter.updateAdapter(movieModels)
                            }
                            is MovieType.NOW_PLAYING->{
                                recommendedAdapter.updateAdapter(movieModels)
                            }
                        }
                    }
                    else -> {/*No emission is observed*/
                    }
                }
            }.launchIn(lifecycleScope)
        }
    }

    //4 saatımı aldı :)
    private fun setMotionAnimation() {
        val viewPager = binding.topMoviesViewPager
        val scrollView = binding.scrollView

        val params = viewPager.layoutParams
        //View Pager will cover 40% of the screen
        val initialHeight = Resources.getSystem().displayMetrics.heightPixels * 0.4
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val movement = scrollView.scrollY.toFloat()
            val percentage = (movement / initialHeight).toFloat()
            val newHeight = initialHeight * (1-percentage)
            viewPager.alpha = (1 - 2 * percentage)
            binding.imageView6.alpha = (1 - 3 * percentage)
            binding.fakeToolbar.alpha = (1.5 * percentage).toFloat()
            binding.textView10.alpha = (1.5 * percentage).toFloat()
            params.height = newHeight.toInt()
            viewPager.requestLayout()
        }
    }

    private fun seeAllSetup(button : Button, movieType: MovieType){
        button.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToSeeAllFragment(movieType))
        }
    }

    private fun setRv(){
        val topTenRv = binding.rvTopTenPopular
        topTenRv.adapter = topTenAdapter
        seeAllSetup(binding.buttonPopularSeeAll,MovieType.TOP_RATED)

        val newReleasesRv = binding.rvNewReleases
        newReleasesRv.adapter = newReleasesAdapter
        seeAllSetup(binding.buttonRecentSeeAll, MovieType.RECENT)

        val recommendedRv = binding.rvRecommended
        recommendedRv.adapter = recommendedAdapter
        seeAllSetup(binding.buttonRecommendedSeeAll, MovieType.NOW_PLAYING)
    }

    private fun setViewPager() {
        val viewPager = binding.topMoviesViewPager
        viewPager.adapter = viewPagerAdapter
    }

    private fun goToExplore(){
        binding.buttonSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToExploreFragment())
        }
    }
}