package com.example.moviesappbootcamp.presentation.onboardingScreens.splashFragment

import androidx.lifecycle.ViewModel
import com.example.moviesappbootcamp.common.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefManager: PrefManager
) : ViewModel() {

    fun getUid() = prefManager.uid
}