package com.example.moviesappbootcamp.presentation.mainScreens.exploreFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.use_case.GetMoviesUseCase
import com.example.moviesappbootcamp.domain.use_case.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase
): ViewModel() {

    //
    private val _pagingData = MutableStateFlow<PagingData<MovieLayoutModel>?>(null)
    val pagingData = _pagingData.asStateFlow()

    private val _result = MutableStateFlow<Resource<List<MovieLayoutModel>>?>(null)
    val result = _result.asStateFlow()

    init {
        defaultMovies()
    }
//
    fun searchMovies(query : String){
        viewModelScope.launch {
            searchMoviesUseCase.invoke(query).collectLatest {
                _pagingData.emit(it)
            }
        }
    }

    fun defaultMovies(){
        viewModelScope.launch {
            getMoviesUseCase(MovieType.POPULAR).collectLatest {
                it.data?.let { data->
                    _result.emit(Resource.Success(data.movieLayoutModels))
                }
            }
        }
    }
}