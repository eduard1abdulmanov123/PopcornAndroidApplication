package com.abdulmanov.MoviCorn.model.vo.person

import com.abdulmanov.MoviCorn.model.vo.movie.FilmLittle

data class FilmographyPerson(
    val cast:List<FilmLittle>,
    val crew:List<FilmLittle>
)