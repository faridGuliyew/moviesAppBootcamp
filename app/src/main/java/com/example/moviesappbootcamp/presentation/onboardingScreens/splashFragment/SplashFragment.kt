package com.example.moviesappbootcamp.presentation.onboardingScreens.splashFragment

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.PrefManager
import com.example.moviesappbootcamp.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    //
    private val prefManager by lazy {
        PrefManager(requireContext())
    }

    override fun onViewCreatedLight() {
        navigate(2000)
    }


    private fun navigate(delayInMillis : Long){
        lifecycleScope.launch {
            delay(delayInMillis)
            val destination =
                if (prefManager.uid != null) SplashFragmentDirections.actionSplashFragmentToMainFragment()
                else SplashFragmentDirections.actionSplashFragmentToIntroFragment()
            try {
                findNavController().navigate(destination)
            }catch (_:Exception){}
        }
    }
}