package com.abdulmanov.MoviCorn.model.mappers.movies

import android.text.TextUtils.split
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.BASE_POSTER_PATH_URL_185
import com.abdulmanov.MoviCorn.model.vo.movie.FilmLittle
import com.abdulmanov.core.network.dto.movies.MoviesDTO
import io.reactivex.functions.Function

class MoviesDTOtoListFilmLittleMapper:Function<MoviesDTO,List<FilmLittle>> {
    override fun apply(moviesDTO: MoviesDTO): List<FilmLittle> {
        return moviesDTO.results.map {
            with(it) {
                FilmLittle(
                    id,
                    title,
                    voteAverage.toString(),
                    releaseDate?.let {date->date.split("-")[0]} ,
                    posterPath?.let { poster-> BASE_POSTER_PATH_URL_185 + poster }
                )
            }
        }.sortedWith(Comparator sort@{ p0, p1 ->
            if (p1.posterPath == null && p0.posterPath != null)
                return@sort -1
            else if (p0.posterPath == null && p1.posterPath != null)
                return@sort 1
            return@sort 0
        })
    }
}