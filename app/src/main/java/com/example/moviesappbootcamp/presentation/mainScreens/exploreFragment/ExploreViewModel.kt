package com.example.moviesappbootcamp.presentation.mainScreens.exploreFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _result = MutableStateFlow<Resource<List<MovieLayoutModel>>?>(null)
    val result = _result.asStateFlow()

    private var currentPage = 1
    private var maxPageCount = 100

    init {
        defaultMovies()
    }

    fun searchMovies(query : String){
        viewModelScope.launch {
            searchMoviesUseCase(query,currentPage).collectLatest {
                when(it){
                    is Resource.Loading->{
                        _result.emit(Resource.Loading())
                    }
                    is Resource.Error->{
                        _result.emit(Resource.Error(it.message?:"Unexpected error occurred"))
                    }
                    is Resource.Success->{
                        _result.emit(Resource.Success(it.data!!.movieLayoutModels))
                        maxPageCount = it.data.totalPage
                    }
                }
            }
            Log.e("explore",maxPageCount.toString())
            if (currentPage < maxPageCount){
                currentPage++
            }else{
                _result.emit(Resource.Error("This is the last page available."))
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

    fun resetPager(){
        currentPage = 1
    }
}