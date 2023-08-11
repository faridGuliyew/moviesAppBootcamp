package com.example.moviesappbootcamp.presentation.onboardingScreens.signInFragment

import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.PrefManager
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.databinding.FragmentSignInBinding
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.example.moviesappbootcamp.utils.fancyToast
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreatedLight() {
        observe()
        setClickEvents()
    }

    private fun observe(){
        val loadingDialog = CustomLoadingDialog(requireContext(),layoutInflater,"Please wait...")
        with(viewModel){
            mailLoginResult.onEach {
                when(it){
                    is Resource.Loading -> {
                        loadingDialog.show()
                    }
                    is Resource.Success -> {
                        fancyToast(requireContext(),"Logged in successfully!",FancyToast.SUCCESS)
                        if (binding.checkBox.isChecked){
                            viewModel.rememberUid(it.data)
                        }
                        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMainFragment())
                        loadingDialog.dismiss()
                    }
                    is Resource.Error -> {
                        fancyToast(requireContext(),it.message!!,FancyToast.ERROR)
                        loadingDialog.dismiss()
                    }
                    else->{}
                }
            }.launchIn(lifecycleScope)
        }
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
    private fun setAuthButton(button: Button, etMail : EditText, etPassword : EditText){
        button.setOnClickListener {
            firebaseAuth(etMail, etPassword)
        }
    }

    private fun firebaseAuth(etMail : EditText, etPassword : EditText){
        val mail = etMail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        if (isValid(mail,password)){
            viewModel.mailLogin(mail, password)
        }
    }
    private fun signUp(text : TextView){
        text.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignupFragment())
        }
    }

    private fun setImeActions(etMail : EditText, etPassword : EditText){
        etMail.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_NEXT){
                etPassword.requestFocus()
                true
            }else{
                false
            }
        }

        etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                firebaseAuth(etMail, etPassword)
                true
            }else{
                false
            }
        }
    }

    private fun isValid(mail : String, password : String) : Boolean{
        val pattern = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        return if (password.length < 6){
            fancyToast(requireContext(),"The length of the password should at least be 6 characters long!",FancyToast.INFO)
            false
        } else if (!pattern.matches(mail)){
            fancyToast(requireContext(),"Mail address is badly formatted.", FancyToast.INFO)
            false
        }else{
            true
        }
    }

    private fun setClickEvents(){
        goBack(binding.buttonBack)
        setImeActions(binding.editTextEmail,binding.editTextPassword)
        facebookAuth(binding.buttonFacebook)
        googleAuth(binding.buttonGoogle)
        nuFlixAuth(binding.buttonNuFlix)
        setAuthButton(binding.buttonFirebaseAuth,binding.editTextEmail,binding.editTextPassword)
        signUp(binding.textViewSignUp)
    }
}