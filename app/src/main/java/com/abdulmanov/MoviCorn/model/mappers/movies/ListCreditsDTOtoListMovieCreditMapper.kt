package com.abdulmanov.MoviCorn.model.mappers.movies


import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.BASE_PROFILE_PATH_URL_185
import com.abdulmanov.MoviCorn.model.vo.movie.MovieCredit
import com.abdulmanov.core.network.dto.movies.ListCreditsDTO
import io.reactivex.functions.Function

class ListCreditsDTOtoListMovieCreditMapper:Function<ListCreditsDTO,List<MovieCredit>> {

    override fun apply(listCreditsDTO: ListCreditsDTO): List<MovieCredit> {
        return mutableListOf<MovieCredit>().apply {
            addAll(
                listCreditsDTO.moviesCast.map {
                    MovieCredit(
                        it.id,
                        it.profilePath?.let { path-> BASE_PROFILE_PATH_URL_185 + path },
                        it.name,
                        it.character
                    )
                }
            )
            addAll(
                listCreditsDTO.moviesCrew.map {
                    MovieCredit(
                        it.id,
                        it.profilePath?.let { path-> BASE_PROFILE_PATH_URL_185 + path},
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
}