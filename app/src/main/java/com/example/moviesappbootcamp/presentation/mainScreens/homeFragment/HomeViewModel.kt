package com.example.moviesappbootcamp.presentation.mainScreens.homeFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.data.Resource
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.use_case.GetTopRatedMoviesUseCase
import com.example.moviesappbootcamp.domain.use_case.GetUpcomingMoviesUseCase
import com.example.moviesappbootcamp.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
) : ViewModel() {

    private val _result = MutableLiveData<UiState<MovieModelWithType>>()
    val result: LiveData<UiState<MovieModelWithType>>
        get() = _result

    init {
        getTopRatedMovies(MovieType.POPULAR)
        getTopRatedMovies(MovieType.TOP_RATED)
        getUpcomingMovies(MovieType.RECENT)
        getUpcomingMovies(MovieType.NOW_PLAYING)
    }

    private fun getTopRatedMovies(movieType: MovieType = MovieType.POPULAR) {
        viewModelScope.launch {
            getTopRatedMoviesUseCase(movieType).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _result.value = UiState.Loading
                    }

                    is Resource.Error -> {
                        _result.value = UiState.Error(it.message)
                    }

                    is Resource.Success -> {
                        _result.value = UiState.Success(it.data)
                    }
                }
            }
        }
    }

    private fun getUpcomingMovies(movieType: MovieType = MovieType.RECENT) {
        viewModelScope.launch {
            getUpcomingMoviesUseCase(movieType).collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        _result.value = UiState.Loading
                    }

                    is Resource.Error -> {
                        _result.value = UiState.Error(it.message)
                    }

                    is Resource.Success -> {
                        _result.value = UiState.Success(it.data)
                    }
                }
            }
        }
    }
}