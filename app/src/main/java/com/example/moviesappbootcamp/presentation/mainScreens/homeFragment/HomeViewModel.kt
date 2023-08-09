package com.example.moviesappbootcamp.presentation.mainScreens.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _result = MutableStateFlow<Resource<MovieModelWithType>?>(null)
    val result = _result.asStateFlow()

    init {
        getMovies(MovieType.POPULAR)
        getMovies(MovieType.TOP_RATED)
        getMovies(MovieType.RECENT)
        getMovies(MovieType.NOW_PLAYING)
    }

    private fun getMovies(movieType: MovieType = MovieType.POPULAR){
        viewModelScope.launch {
            getMoviesUseCase(movieType).collectLatest {
                _result.emit(it)
            }
        }
    }
}