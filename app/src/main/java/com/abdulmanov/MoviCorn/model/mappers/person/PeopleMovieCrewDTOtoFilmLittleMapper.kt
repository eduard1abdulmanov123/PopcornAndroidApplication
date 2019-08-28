package com.abdulmanov.MoviCorn.model.mappers.person

import com.abdulmanov.MoviCorn.common.Constants
import com.abdulmanov.MoviCorn.model.vo.movie.FilmLittle
import com.abdulmanov.core.network.dto.people.PeopleMovieCrewDTO
import io.reactivex.functions.Function

class PeopleMovieCrewDTOtoFilmLittleMapper:Function<PeopleMovieCrewDTO, FilmLittle> {
    override fun apply(peopleMovieCrewDTO: PeopleMovieCrewDTO): FilmLittle {
        return with(peopleMovieCrewDTO){
            FilmLittle(
                id,
                title,
                voteAverage.toString(),
                releaseData?.let { it.split("-")[0] },
                posterPath?.let { Constants.Network.BASE_POSTER_PATH_URL_185 + it }
            )
        }
    }
}