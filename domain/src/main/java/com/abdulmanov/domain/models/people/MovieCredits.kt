package com.abdulmanov.domain.models.people

import com.abdulmanov.domain.models.movies.MovieShort

data class MovieCredits (
    val cast:List<MovieShort>,
    val crew:List<MovieShort>
)