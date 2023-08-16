package com.example.moviesappbootcamp.presentation.onboardingScreens.signUpFragment

import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.databinding.FragmentSignupBinding
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate) {

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreatedLight() {
        observe()
        setClickEvents()
    }


    private fun observe(){
        val loadingDialog = CustomLoadingDialog(requireContext(),layoutInflater,"Please wait...")
        with(viewModel){
            mailSignUpResult.onEach {
                when(it){
                    is Resource.Loading -> {
                        loadingDialog.show()
                    }
                    is Resource.Success -> {
                        fancyToast(requireContext(),"Account created successfully!",FancyToast.SUCCESS)
                        if (binding.checkBox.isChecked){
                            rememberUid(it.data)
                        }
                        findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToMainFragment())
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
    private fun setAuthButton(button: Button,etMail : EditText, etPassword : EditText){
        button.setOnClickListener {
            firebaseAuth(etMail, etPassword)
        }
    }

    private fun firebaseAuth(etMail : EditText, etPassword : EditText){
        val mail = etMail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        if (isValid(mail,password)){
            viewModel.mailSignUp(mail, password)
        }
    }

    private fun login(text : TextView){
        text.setOnClickListener {
            findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToSignInFragment())
        }
    }

    private fun isValid(mail : String, password : String) : Boolean{
        val pattern = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        return if (password.length < 6){
            fancyToast(requireContext(),"The length of the password should at least be 6 characters long!",FancyToast.INFO)
            false
        } else if (!pattern.matches(mail)){
            fancyToast(requireContext(),"Mail address is badly formatted.",FancyToast.INFO)
            false
        }else{
            true
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

    private fun setClickEvents(){
        goBack(binding.buttonBack)
        facebookAuth(binding.buttonFacebook)
        googleAuth(binding.buttonGoogle)
        nuFlixAuth(binding.buttonNuFlix)
        setAuthButton(binding.buttonFirebaseAuth,binding.editTextEmail,binding.editTextPassword)
        setImeActions(binding.editTextEmail,binding.editTextPassword)
        login(binding.textViewLogin)
    }

}