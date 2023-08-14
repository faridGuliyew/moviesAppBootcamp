package com.example.moviesappbootcamp.presentation.mainScreens.exploreFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _pagingData = MutableLiveData<PagingData<MovieLayoutModel>>()
    val pagingData : LiveData<PagingData<MovieLayoutModel>>
        get() = _pagingData


    private val _uiState = MutableLiveData<ExploreUiState>()
    val uiState : LiveData<ExploreUiState>
        get() = _uiState

    init {
        defaultMovies()
    }
//
    fun searchMovies(query : String){
        viewModelScope.launch {
            searchMoviesUseCase.invoke(query).cachedIn(this).collectLatest {
                _pagingData.postValue(it)
            }
        }
    }

    fun defaultMovies(){
        viewModelScope.launch {
            getMoviesUseCase(MovieType.POPULAR).collectLatest {
                when(it){
                    is Resource.Loading->{
                        _uiState.postValue(ExploreUiState.Loading)
                    }
                    is Resource.Success->{
                        _uiState.postValue(ExploreUiState.Success(it.data?.movieLayoutModels.orEmpty()))
                    }
                    is Resource.Error->{
                        _uiState.postValue(ExploreUiState.Error)
                    }
                }
            }
        }
    }
}