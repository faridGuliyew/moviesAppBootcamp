package com.example.moviesappbootcamp.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesappbootcamp.data.remote.dto.top_rated.ResultDto
import com.example.moviesappbootcamp.data.remote.dto.top_rated.TopRatedResponseDto
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel

const val firstPageIndex = 1

class MoviePagingSource(
    private val apiService: MovieApi,
    private val query: String,
) : PagingSource<Int, ResultDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultDto> {
        val page = params.key ?: firstPageIndex
        return try {
            val response = apiService.searchMovie(query, page)
            LoadResult.Page(
                data = response.body()?.resultDtos.orEmpty(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.body()?.resultDtos?.isEmpty() == true) null else page.plus(1)
            )
        } catch (e: Exception) {
            Log.e("pagingsource-e", e.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}



