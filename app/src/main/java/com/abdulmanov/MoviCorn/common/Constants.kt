package com.abdulmanov.MoviCorn.common

interface Constants {

    interface Network{
        companion object{
            const val BASE_PROFILE_PATH_URL_185 = "http://image.tmdb.org/t/p/w185/"
            const val BASE_PROFILE_PATH_URL_550 = "http://image.tmdb.org/t/p/h632"
            const val BASE_POSTER_PATH_URL_550 = "http://image.tmdb.org/t/p/w780"
            const val BASE_POSTER_PATH_URL_185 = "http://image.tmdb.org/t/p/w185/"
            const val YOUTUBE_THUMBNAIL_BASE_URL = "http://img.youtube.com/vi/"
            const val YOUTUBE_THUMBNAIL_IMAGE_QUALITY = "/hqdefault.jpg"
            const val YOUTUBE_WATCH_BASE_URL = "https://www.youtube.com/watch?v="

            /*constant_movie*/
            const val THE_MOVIE_DB_MOVIE_BASE_URL="https://www.themoviedb.org/movie/"

            /*constant_person*/
            const val IMDB_PERSON_BASE_URL = "https://www.imdb.com/name/"
            const val THE_MOVIE_DB_PERSON_BASE_URL = "https://www.themoviedb.org/person/"
            const val TWITTER_PERSON_BASE_URL = "https://twitter.com/"
            const val INSTAGRAM_PERSON_BASE_URL = "https://www.instagram.com/"
            const val FACEBOOK_PERSON_BASE_URL = "https://ru-ru.facebook.com/"
        }
    }
}