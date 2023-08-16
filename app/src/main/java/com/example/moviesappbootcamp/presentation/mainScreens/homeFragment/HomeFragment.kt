package com.example.moviesappbootcamp.presentation.mainScreens.homeFragment

import android.content.res.Resources
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.databinding.FragmentHomeBinding
import com.example.moviesappbootcamp.presentation.adapter.rv.MovieSmallAdapter
import com.example.moviesappbootcamp.presentation.adapter.vp.TopRatedViewPagerAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.presentation.UiState
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel by viewModels<HomeViewModel>()
    private val viewPagerAdapter = TopRatedViewPagerAdapter()
    private val topTenAdapter = MovieSmallAdapter()
    private val newReleasesAdapter = MovieSmallAdapter()
    private val recommendedAdapter = MovieSmallAdapter()

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
            result.observe(viewLifecycleOwner){
                when(it){
                    is UiState.Loading-> loadingDialog.show()
                    is UiState.Error -> {
                        loadingDialog.dismiss()
                        fancyToast(requireContext(),it.message,FancyToast.ERROR)
                    }
                    is UiState.Success -> {
                        loadingDialog.dismiss()
                        val data = it.data.movieBriefUiModels
                        when(it.data.movieType){
                            is MovieType.TOP_RATED->topTenAdapter.updateAdapter(data)
                            is MovieType.POPULAR->viewPagerAdapter.updateAdapter(data)
                            is MovieType.RECENT->newReleasesAdapter.updateAdapter(data)
                            is MovieType.NOW_PLAYING -> recommendedAdapter.updateAdapter(data)
                        }
                    }
                }
            }
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

    private fun setRv(){
        rvSetup(binding.rvTopTenPopular,topTenAdapter,MovieType.TOP_RATED)
        rvSetup(binding.rvNewReleases,newReleasesAdapter,MovieType.RECENT)
        rvSetup(binding.rvRecommended,recommendedAdapter,MovieType.NOW_PLAYING)
    }

    private fun setViewPager() {
        val viewPager = binding.topMoviesViewPager
        viewPager.adapter = viewPagerAdapter

        val transformer = CompositePageTransformer()
        transformer.addTransformer { page, position ->
            page.scaleX = abs(1 - 0.85*position).toFloat()
            page.scaleY = abs(1 - 0.85*position).toFloat()
        }
        viewPager.setPageTransformer(transformer)
    }

    private fun goToExplore(){
        binding.buttonSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToExploreFragment())
        }
    }

    /*Setup*/
    private fun seeAllSetup(button : Button, movieType: MovieType){
        button.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToSeeAllFragment(movieType))
        }
    }

    private fun rvSetup(rv : RecyclerView, adapter : MovieSmallAdapter, movieType: MovieType){
        rv.adapter = adapter
        adapter.setOnClickEvent { id->
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToDetailsFragment(id))
        }
        seeAllSetup(binding.buttonPopularSeeAll,movieType)
    }
}