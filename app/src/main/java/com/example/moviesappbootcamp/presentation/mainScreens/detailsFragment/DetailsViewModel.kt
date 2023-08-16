package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.model.MovieDetailedUiModel
import com.example.moviesappbootcamp.domain.use_case.GetMovieCreditsUseCase
import com.example.moviesappbootcamp.domain.use_case.GetSingleMovieUseCase
import com.example.moviesappbootcamp.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getSingleMovieUseCase: GetSingleMovieUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase
) : ViewModel(){

    private val _uiState = MutableLiveData<DetailsUiState<*>>()
    val uiState : LiveData<DetailsUiState<*>>
        get() = _uiState


    fun getSingleMovie(movieId : Int){
        viewModelScope.launch {
            getSingleMovieUseCase(movieId).collect{
                when(it){
                    is Resource.Loading-> _uiState.value = DetailsUiState.Loading("Movie details are loading...")
                    is Resource.Error-> _uiState.value = DetailsUiState.Error(it.message)
                    is Resource.Success-> {
                        val data = it.data
                        _uiState.value = if (data!=null) DetailsUiState.Success(data, DetailsDataType.MOVIE_DETAILS) else DetailsUiState.Unavailable("Movie does not exist in our database")
                        getMovieCredits(movieId)
                    }
                }
            }
        }
    }

    private fun getMovieCredits(movieId: Int){
        viewModelScope.launch {
            getMovieCreditsUseCase.invoke(movieId).collect{
                when(it){
                    is Resource.Loading-> _uiState.value = DetailsUiState.Loading("Cast and Crew members are loading")
                    is Resource.Error-> _uiState.value = DetailsUiState.Error(it.message)
                    is Resource.Success-> {
                        val data = it.data
                        _uiState.value = if (data!=null) DetailsUiState.Success(data, DetailsDataType.CREDITS) else DetailsUiState.Unavailable("Credits for this movies does not exist in our database")
                    }
                }
            }
        }
    }
}