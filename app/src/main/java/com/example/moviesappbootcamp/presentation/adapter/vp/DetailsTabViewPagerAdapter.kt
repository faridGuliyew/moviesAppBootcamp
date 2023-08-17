package com.example.moviesappbootcamp.presentation.adapter.vp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.tab_layout_fragments.recommended.RecommendedMoviesTabFragment

class DetailsTabViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, private val id : Int) : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> RecommendedMoviesTabFragment(id)
            else -> Fragment()
        }
    }
}