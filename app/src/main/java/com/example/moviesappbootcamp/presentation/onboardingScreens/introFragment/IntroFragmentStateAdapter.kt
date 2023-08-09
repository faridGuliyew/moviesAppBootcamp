package com.example.moviesappbootcamp.presentation.onboardingScreens.introFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviesappbootcamp.presentation.onboardingScreens.introFragment.view_pager_fragments.IntroPagerFragmentOne
import com.example.moviesappbootcamp.presentation.onboardingScreens.introFragment.view_pager_fragments.IntroPagerFragmentThree
import com.example.moviesappbootcamp.presentation.onboardingScreens.introFragment.view_pager_fragments.IntroPagerFragmentTwo

class IntroFragmentStateAdapter(fragmentManager : FragmentManager, lifecycle : Lifecycle)  : FragmentStateAdapter(fragmentManager,lifecycle){


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->IntroPagerFragmentOne()
            1->IntroPagerFragmentTwo()
            2->IntroPagerFragmentThree()
            else-> Fragment()
        }
    }
}