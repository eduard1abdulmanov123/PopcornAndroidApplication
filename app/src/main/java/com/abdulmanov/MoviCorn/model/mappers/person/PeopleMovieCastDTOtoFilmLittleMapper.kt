package com.abdulmanov.MoviCorn.model.mappers.person

import com.abdulmanov.MoviCorn.common.Constants
import com.abdulmanov.MoviCorn.model.vo.movie.FilmLittle
import com.abdulmanov.core.network.dto.people.PeopleMovieCastDTO
import io.reactivex.functions.Function

class PeopleMovieCastDTOtoFilmLittleMapper:Function<PeopleMovieCastDTO, FilmLittle> {
    override fun apply(peopleMovieCastDTO: PeopleMovieCastDTO): FilmLittle {
        return with(peopleMovieCastDTO){
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