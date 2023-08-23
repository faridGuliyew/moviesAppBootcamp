package com.example.moviesappbootcamp.presentation.mainScreens.profileFragment

import android.content.res.Resources
import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.model.other.PreferencesModel
import com.example.moviesappbootcamp.databinding.FragmentProfileBinding
import com.example.moviesappbootcamp.presentation.UiState
import com.example.moviesappbootcamp.presentation.adapter.rv.PreferencesRvAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val preferencesAdapter = PreferencesRvAdapter()

    override fun onViewCreatedLight() {

        setRv()
        shineAnimation()
        goToPayment()
        logout()
    }

    private fun setRv(){
        binding.preferencesRv.adapter = preferencesAdapter
        preferencesAdapter.setOnItemClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
        }
        setPreferences()
    }


    private fun shineAnimation() {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.left_right)
        binding.shine.startAnimation(anim)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {}
            override fun onAnimationEnd(p0: Animation?) { binding.shine.startAnimation(anim) }
            override fun onAnimationRepeat(p0: Animation?) {} })
    }

    private fun goToPayment(){
        binding.buttonPremium.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToPaymentFragment())
        }
    }

    private fun setPreferences(){
        val preferences = arrayListOf<PreferencesModel>()
        preferences.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.pref_account,context?.theme),"Edit profile"))
        preferences.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.pref_notification,context?.theme),"Notifications"))
        preferences.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.ic_download,context?.theme),"Downloads"))
        preferences.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.pref_security,context?.theme),"Security"))
        preferences.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.pref_globe,context?.theme),"Language","English(US)"))
        preferences.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.pref_help,context?.theme),"Help center"))
        preferences.add(PreferencesModel(ResourcesCompat.getDrawable(resources, R.drawable.pref_privacy,context?.theme),"Privacy and policy"))
        preferencesAdapter.differ.submitList(preferences)
    }

    private fun logout(){
        binding.buttonExit.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLogOutFragment())
        }
    }
}