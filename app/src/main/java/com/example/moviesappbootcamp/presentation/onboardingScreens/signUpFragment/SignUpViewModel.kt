package com.example.moviesappbootcamp.presentation.onboardingScreens.signUpFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.PrefManager
import com.example.moviesappbootcamp.common.model.data.Resource
import com.example.moviesappbootcamp.domain.use_case.MailSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val mailSignUpUseCase: MailSignUpUseCase,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _mailSignUpResult = MutableStateFlow<Resource<String>?>(null)
    val mailSignUpResult = _mailSignUpResult.asStateFlow()

    fun mailSignUp(mail : String,password : String){
        viewModelScope.launch {
            mailSignUpUseCase(mail, password).collectLatest {
                _mailSignUpResult.emit(it)
            }
        }
    }
    fun rememberUid(uid : String?){
        prefManager.uid = uid
    }
}