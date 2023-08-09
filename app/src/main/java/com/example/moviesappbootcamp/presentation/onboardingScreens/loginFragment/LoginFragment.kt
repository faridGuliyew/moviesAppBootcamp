package com.example.moviesappbootcamp.presentation.onboardingScreens.loginFragment

import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun onViewCreatedLight() {

        setClickEvents()
    }

    private fun goBack(buttonBack : Button){
        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun facebookAuth(button: Button){
        button.setOnClickListener {

        }
    }
    private fun googleAuth(button: Button){
        button.setOnClickListener {

        }
    }
    private fun nuFlixAuth(button: Button){
        button.setOnClickListener {

        }
    }
    private fun goToSignIn(button: Button){
        button.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignInFragment())
        }
    }
    private fun signUp(text : TextView){
        text.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
    }

    private fun setClickEvents(){
        goBack(binding.buttonBack)
        facebookAuth(binding.buttonFacebook)
        googleAuth(binding.buttonGoogle)
        nuFlixAuth(binding.buttonNuFlix)
        goToSignIn(binding.buttonFirebaseAuth)
        signUp(binding.textViewSignUp)
    }

}