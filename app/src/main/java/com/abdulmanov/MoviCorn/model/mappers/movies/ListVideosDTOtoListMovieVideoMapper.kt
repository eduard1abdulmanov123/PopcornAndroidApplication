package com.abdulmanov.MoviCorn.model.mappers.movies

import com.abdulmanov.MoviCorn.common.Constants
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.YOUTUBE_THUMBNAIL_BASE_URL
import com.abdulmanov.MoviCorn.common.Constants.Network.Companion.YOUTUBE_THUMBNAIL_IMAGE_QUALITY
import com.abdulmanov.MoviCorn.model.vo.movie.MovieVideo
import com.abdulmanov.core.network.dto.movies.ListVideosDTO
import io.reactivex.functions.Function

class ListVideosDTOtoListMovieVideoMapper:Function<ListVideosDTO,List<MovieVideo>> {
    override fun apply(listVideoDTO: ListVideosDTO): List<MovieVideo> {
        return listVideoDTO.results.filter { it.site == "YouTube" }.map {
            MovieVideo(
                it.key,
                YOUTUBE_THUMBNAIL_BASE_URL + it.key + YOUTUBE_THUMBNAIL_IMAGE_QUALITY
            )
        }
    }
}