package com.example.moviesappbootcamp.presentation.onboardingScreens.introFragment.view_pager_fragments

import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.databinding.FragmentIntroPagerThreeBinding
import com.example.moviesappbootcamp.presentation.onboardingScreens.introFragment.IntroFragmentDirections


class IntroPagerFragmentThree : BaseFragment<FragmentIntroPagerThreeBinding>(FragmentIntroPagerThreeBinding::inflate) {

    override fun onViewCreatedLight() {
        goToLogin(binding.buttonStart)
    }

    private fun goToLogin(button : Button){
        button.setOnClickListener {
            findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
        }
    }

}