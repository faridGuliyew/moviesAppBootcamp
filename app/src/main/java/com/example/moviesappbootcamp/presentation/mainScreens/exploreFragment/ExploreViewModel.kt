package com.example.moviesappbootcamp.presentation.mainScreens.exploreFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.use_case.GetTopRatedMoviesUseCase
import com.example.moviesappbootcamp.domain.use_case.GetUpcomingMoviesUseCase
import com.example.moviesappbootcamp.domain.use_case.SearchMoviesUseCase
import com.example.moviesappbootcamp.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
): ViewModel() {


    private val _pagingData = MutableLiveData<PagingData<MovieBriefUiModel>>()
    val pagingData : LiveData<PagingData<MovieBriefUiModel>>
        get() = _pagingData


    private val _uiState = MutableLiveData<UiState<List<MovieBriefUiModel>>>()
    val uiState : LiveData<UiState<List<MovieBriefUiModel>>>
        get() = _uiState

    init {
        defaultMovies()
    }

    fun searchMovies(query : String){
        viewModelScope.launch {
            searchMoviesUseCase.invoke(query).cachedIn(this).collectLatest {
                _pagingData.postValue(it)
            }
        }
    }

    fun defaultMovies(){
        viewModelScope.launch {
            getTopRatedMoviesUseCase(MovieType.POPULAR).collectLatest {
                when(it){
                    is Resource.Loading->{
                        _uiState.postValue(UiState.Loading)
                    }
                    is Resource.Success->{
                        _uiState.postValue(UiState.Success(it.data.movieBriefUiModels))
                    }
                    is Resource.Error->{
                        _uiState.postValue(UiState.Error(it.message))
                    }
                }
            }
        }
    }
}