package com.example.moviesappbootcamp.data.mapper

import com.example.moviesappbootcamp.common.model.other.ChipFilter
import com.example.moviesappbootcamp.common.filter.GenreFilter
import com.example.moviesappbootcamp.data.remote.dto.top_rated.ResultDto
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.model.MovieDetailedUiModel
import com.example.moviesappbootcamp.common.utils.releaseDateToYear
import com.example.moviesappbootcamp.data.remote.dto.credits.Cast
import com.example.moviesappbootcamp.data.remote.dto.credits.Crew
import com.example.moviesappbootcamp.data.remote.dto.reviews.ReviewsResponseDto
import com.example.moviesappbootcamp.data.remote.dto.single.SingleMovieResponseDto
import com.example.moviesappbootcamp.data.remote.dto.upcoming.UpcomingResultDto
import com.example.moviesappbootcamp.data.remote.dto.videos.VideosResponseDto
import com.example.moviesappbootcamp.domain.model.CreditsUiModel
import com.example.moviesappbootcamp.domain.model.ReviewUiModel
import com.example.moviesappbootcamp.domain.model.TrailerUiModel


fun List<ResultDto>.toBriefUiModels(): List<MovieBriefUiModel> {
    return map { dto ->
        MovieBriefUiModel(
            movieId = dto.id,
            movieName = dto.title,
            moviePoster = dto.posterPath,
            movieBackdrop = dto.backdropPath,
            movieOverview = dto.overview,
            movieRating = dto.voteAverage
        )
    }
}

fun ResultDto.toBriefUiModel(): MovieBriefUiModel {
    return MovieBriefUiModel(
        movieId = id,
        movieName = title,
        moviePoster = posterPath,
        movieBackdrop = backdropPath,
        movieOverview = overview,
        movieRating = voteAverage
    )
}


fun SingleMovieResponseDto.toDetailedUiModel(): MovieDetailedUiModel {
    return MovieDetailedUiModel(
        movieId = id,
        movieName = title,
        moviePoster = posterPath,
        movieBackdrop = backdropPath,
        movieOverview = overview,
        movieRating = voteAverage,
        voteCount = voteCount,
        movieReleaseYear = releaseDateToYear(releaseDate),
        movieGenres = genres.map { genre-> ChipFilter.Genre(GenreFilter.findGenreById(genre.id)) }
    )
}


fun List<UpcomingResultDto>.toBrieffUiModels(): List<MovieBriefUiModel> {
    return map { dto ->
        dto.toBriefUiModel()
    }
}

fun UpcomingResultDto.toBriefUiModel(): MovieBriefUiModel {
    return MovieBriefUiModel(
        movieId = id,
        movieName = title,
        moviePoster = posterPath,
        movieBackdrop = backdropPath,
        movieOverview = overview,
        movieRating = voteAverage
    )
}

fun List<Cast>.toCreditsUiModel() = map {
    CreditsUiModel(
        id = it.id,
        name = it.name,
        role = it.character,
        profilePath = it.profilePath
    )
}

fun List<Crew>.toCredittsUiModel() = map {
    CreditsUiModel(
        id = it.id,
        name = it.name,
        role = it.job,
        profilePath = it.profilePath
    )
}

fun ReviewsResponseDto.toReviewUiModels() : List<ReviewUiModel>{
    return results.map { reviewResult ->
        ReviewUiModel(
            username = reviewResult.authorDetails.username,
            profilePath = reviewResult.authorDetails.avatarPath?:"broken",
            comment = reviewResult.content,
            rating = reviewResult.authorDetails.rating,
            date = reviewResult.createdAt
        )
    }
}

fun VideosResponseDto.toTrailerUiModels() : List<TrailerUiModel>{

    return this.videoResults.map { result->
        TrailerUiModel(
            id = result.id,
            key = result.key,
            name = result.name,
            site = result.site
        )
    }

}
