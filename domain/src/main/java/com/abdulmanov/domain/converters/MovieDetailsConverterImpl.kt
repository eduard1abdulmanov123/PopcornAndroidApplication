package com.abdulmanov.domain.converters

import com.abdulmanov.core.remote.models.movies.*
import com.abdulmanov.domain.helpers.Constants.Companion.BASE_IMAGE_URL_185
import com.abdulmanov.domain.helpers.Constants.Companion.BASE_IMAGE_URL_780
import com.abdulmanov.domain.helpers.Constants.Companion.SITE
import com.abdulmanov.domain.helpers.Constants.Companion.THE_MOVIE_DB_MOVIE_BASE_URL
import com.abdulmanov.domain.helpers.Constants.Companion.YOUTUBE_THUMBNAIL_BASE_URL
import com.abdulmanov.domain.helpers.Constants.Companion.YOUTUBE_THUMBNAIL_IMAGE_QUALITY
import com.abdulmanov.domain.helpers.Constants.Companion.YOUTUBE_WATCH_BASE_URL
import com.abdulmanov.domain.models.movies.*
import com.abdulmanov.domain.models.movies.Credit
import com.abdulmanov.domain.models.movies.Video

class MovieDetailsConverterImpl(private val moviesConverter: MoviesConverter) : MovieDetailsConverter {

    override fun convertFromMovieDetailsResponseToMovieDetails(
        movieDetailsResponse: MovieDetailsResponse,
        inLocalDb: Boolean
    ): MovieDetails {
        return with(movieDetailsResponse) {
            MovieDetails(
                id,
                poster_path?.let { BASE_IMAGE_URL_780 + it },
                title,
                original_title,
                release_date,
                vote_average,
                vote_count,
                convertListProductionCountryApiToListCountry(production_countries),
                genres.map { it.name },
                runtime,
                "$${String.format("%,d", budget).replace(',', ' ')}",
                "$${String.format("%,d", revenue).replace(',', ' ')}",
                overview,
                THE_MOVIE_DB_MOVIE_BASE_URL + id,
                convertCreditsApiToListCredit(credits),
                convertResultVideosApiToListVideo(videos),
                moviesConverter.convertFromMoviesResponseToListMovieShort(similar),
                inLocalDb
            )
        }
    }

    override fun convertListProductionCountryApiToListCountry(listProductionCountryApi: List<ProductionCountryApi>): List<String> {
        return listProductionCountryApi.map { it.name }
    }

    override fun convertCreditsApiToListCredit(creditsApi: CreditsApi): List<Credit> {
        return mutableListOf<Credit>().apply {
            addAll(
                creditsApi.cast.map {
                    Credit(
                        it.id,
                        it.profile_path?.let { path -> BASE_IMAGE_URL_185 + path },
                        it.name,
                        it.character
                    )
                }
            )
            addAll(
                creditsApi.crew.map {
                    Credit(
                        it.id,
                        it.profile_path?.let { path -> BASE_IMAGE_URL_185 + path },
                        it.name,
                        it.job
                    )
                }
            )
        }.sortedWith(Comparator sort@{ p0, p1 ->
            if (p1.profilePath == null && p0.profilePath != null)
                return@sort -1
            else if (p0.profilePath == null && p1.profilePath != null)
                return@sort 1
            return@sort 0
        })
    }

    override fun convertResultVideosApiToListVideo(resultVideosApi: ResultVideosApi): List<Video> {
        return resultVideosApi.results.filter { it.site == SITE }.map {
            Video(
                YOUTUBE_WATCH_BASE_URL + it.key,
                YOUTUBE_THUMBNAIL_BASE_URL + it.key + YOUTUBE_THUMBNAIL_IMAGE_QUALITY
            )
        }
    }

}