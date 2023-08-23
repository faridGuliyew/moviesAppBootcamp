package com.example.moviesappbootcamp.presentation.mainScreens.profileFragment.logOutFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.model.data.Resource
import com.example.moviesappbootcamp.domain.use_case.LogOutUseCase
import com.example.moviesappbootcamp.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogOutViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _state = MutableLiveData<UiState<String>>()
    val state : LiveData<UiState<String>>
        get() = _state

    fun logout(){
        viewModelScope.launch {
            logOutUseCase().collectLatest {
                when(it){
                    is Resource.Loading-> _state.value = UiState.Loading
                    is Resource.Error->{}
                    is Resource.Success -> _state.value = UiState.Success(it.data)
                }
            }
        }
    }
}