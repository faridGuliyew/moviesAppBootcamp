package com.example.moviesappbootcamp.presentation.mainScreens.seeAllFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    private val _result = MutableStateFlow<Resource<MovieModelWithType>?>(null)
    val result = _result.asStateFlow()

    fun getMovies(movieType: MovieType){
        viewModelScope.launch {
            getMoviesUseCase(movieType).collectLatest {
                _result.emit(it)
            }
        }
    }
}