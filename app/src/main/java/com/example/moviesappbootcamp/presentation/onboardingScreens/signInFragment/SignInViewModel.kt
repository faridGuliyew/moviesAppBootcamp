package com.example.moviesappbootcamp.presentation.onboardingScreens.signInFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.PrefManager
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.domain.use_case.MailLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val mailLoginUseCase: MailLoginUseCase,
    private val prefManager: PrefManager
) : ViewModel() {

    private val _mailLoginResult = MutableStateFlow<Resource<String>>(Resource.Loading())
    val mailLoginResult = _mailLoginResult.asStateFlow()

    fun mailLogin(mail : String,password : String){
        viewModelScope.launch {
            mailLoginUseCase(mail, password).collectLatest {
                _mailLoginResult.emit(it)
            }
        }
    }
    fun rememberUid(uid : String?){
        prefManager.uid = uid
    }
}