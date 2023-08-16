package com.example.moviesappbootcamp.presentation.mainScreens.seeAllFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.use_case.GetTopRatedMoviesUseCase
import com.example.moviesappbootcamp.domain.use_case.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
): ViewModel() {

    private val _result = MutableStateFlow<Resource<MovieModelWithType>?>(null)
    val result = _result.asStateFlow()

    fun getMovies(movieType: MovieType){
        viewModelScope.launch {
            if (movieType == MovieType.POPULAR || movieType == MovieType.TOP_RATED){
                getTopRatedMoviesUseCase(movieType).collectLatest {
                    _result.emit(it)
                }
            }else{
                getUpcomingMoviesUseCase(movieType).collectLatest {
                    _result.emit(it)
                }
            }
        }
    }
}