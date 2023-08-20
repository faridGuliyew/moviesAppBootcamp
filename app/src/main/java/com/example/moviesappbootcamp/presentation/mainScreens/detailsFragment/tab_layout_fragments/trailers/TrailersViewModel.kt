package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.tab_layout_fragments.trailers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.model.data.Resource
import com.example.moviesappbootcamp.domain.model.TrailerUiModel
import com.example.moviesappbootcamp.domain.use_case.GetVideosUseCase
import com.example.moviesappbootcamp.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailersViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase
) : ViewModel(){

    private val _state = MutableLiveData<UiState<List<TrailerUiModel>>>()
    val state : LiveData<UiState<List<TrailerUiModel>>>
        get() = _state

    fun getVideos(movieId : Int){
        viewModelScope.launch {
            getVideosUseCase(movieId).collectLatest {
                when(it){
                    is Resource.Loading-> _state.value = UiState.Loading
                    is Resource.Error -> _state.value = UiState.Error(it.message)
                    is Resource.Success-> _state.value = UiState.Success(it.data)
                }
            }
        }
    }
}