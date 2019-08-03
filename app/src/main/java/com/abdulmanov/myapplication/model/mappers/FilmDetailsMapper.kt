package com.abdulmanov.myapplication.model.mappers

import android.content.Context
import android.util.Log
import com.abdulmanov.core.network.dto.movies.FilmDetailsDTO
import com.abdulmanov.myapplication.R
import com.abdulmanov.myapplication.model.vo.Credit
import com.abdulmanov.myapplication.model.vo.FilmDetails
import com.abdulmanov.myapplication.model.vo.Video
import io.reactivex.functions.Function
import kotlin.Comparator

class FilmDetailsMapper(private val context:Context):Function<FilmDetailsDTO,FilmDetails> {

    override fun apply(filmDetailsDTO: FilmDetailsDTO): FilmDetails {
        val creditsVO = mutableListOf<Credit>()
        with(filmDetailsDTO.credits) {
            creditsVO.addAll(cast.map { Credit(it.profilePath, it.name, "Actor") })
            creditsVO.addAll(crew.map { Credit(it.profilePath , it.name, it.job) })
        }

        creditsVO.sortWith(Comparator sort@{ p0, p1 ->
            if(p1.profilePath==null&&p0.profilePath!=null)
                return@sort -1
            else if(p0.profilePath==null&&p1.profilePath!=null)
                return@sort 1
            return@sort 0
        })

        val videosVO = filmDetailsDTO.videos.results.filter { it.thumbnail!=null }
            .map { Video(it.path, it.thumbnail!!) }
        val countriesVO = filmDetailsDTO.productionCountries.map { it.name }
        val genresVO = filmDetailsDTO.genres.map { it.name }
        return with(filmDetailsDTO) {
            FilmDetails(
                id,
                posterPath ?: "",
                title,
                originalTitle,
                releaseDate.split("-")[0],
                voteAverage.toString(),
                getVoteCount(voteCount),
                countriesVO,
                genresVO,
                getRuntime(runtime),
                "$${String.format("%,d",budget).replace(',',' ')}",
                "$${String.format("%,d",revenue).replace(',',' ')}",
                overview,
                creditsVO,
                videosVO
            )
        }
    }

    private fun getRuntime(runtime: Int): String {
        val hoursNumber = runtime / 60
        val minutesNumber = runtime % 60

        val hoursString =
            if (hoursNumber == 0)
                ""
            else
                context.resources.getQuantityString(
                    R.plurals.runtime_hours,
                    hoursNumber,
                    hoursNumber
                )+" "

        val minutesString =
            if (minutesNumber == 0)
                ""
            else
                context.resources.getQuantityString(
                    R.plurals.runtime_minutes,
                    minutesNumber,
                    minutesNumber
                )
        return "$hoursString$minutesString"
    }

    private fun getVoteCount(voteCount:Long):String{
        return if(voteCount==0.toLong())
            context.getString(R.string.vote_count_zero)
        else
            context.resources.getQuantityString(R.plurals.vote_count,voteCount.toInt(),voteCount.toInt())
    }
}