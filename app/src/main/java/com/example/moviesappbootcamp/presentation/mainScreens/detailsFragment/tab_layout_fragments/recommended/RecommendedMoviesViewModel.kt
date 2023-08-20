package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.tab_layout_fragments.recommended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.model.data.Resource
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.use_case.GetRecommendedMoviesUseCase
import com.example.moviesappbootcamp.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendedMoviesViewModel @Inject constructor(
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase
) : ViewModel() {

    private val _result = MutableLiveData<UiState<List<MovieBriefUiModel>>>()
    val result : LiveData<UiState<List<MovieBriefUiModel>>>
        get() = _result


    fun getRecommendedMovies(movieId : Int){
        viewModelScope.launch {
            getRecommendedMoviesUseCase(movieId).collect {
                when(it){
                    is Resource.Loading -> _result.value = UiState.Loading
                    is Resource.Error -> _result.value = UiState.Error(it.message)
                    is Resource.Success -> _result.value = UiState.Success(it.data)
                }
            }
        }
    }
}