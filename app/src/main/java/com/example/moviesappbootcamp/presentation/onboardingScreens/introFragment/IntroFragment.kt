package com.example.moviesappbootcamp.presentation.onboardingScreens.introFragment

import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.databinding.FragmentIntroBinding
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlin.math.abs

class IntroFragment : BaseFragment<FragmentIntroBinding>(FragmentIntroBinding::inflate) {

    override fun onViewCreatedLight() {
        setViewPager(binding.introViewPager,binding.dotsIndicator)
    }


    private fun setViewPager(viewPager : ViewPager2, dots : DotsIndicator){
        viewPager.adapter = IntroFragmentStateAdapter(childFragmentManager,lifecycle)
        dots.attachTo(viewPager)
        viewPagerTransformer(viewPager)
    }

    private fun viewPagerTransformer(viewPager: ViewPager2){
        val transformer = CompositePageTransformer()
        transformer.addTransformer { page, position ->

            page.alpha = (1 - abs(position*0.9)).toFloat()
        }
        viewPager.setPageTransformer(transformer)
    }

}