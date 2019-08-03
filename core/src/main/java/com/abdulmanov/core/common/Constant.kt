package com.abdulmanov.core.common

interface Constant {

    interface Thread{
        companion object {
            const val UI_THREAD = "UI_THREAD"
            const val IO_THREAD = "IO_THREAD"
        }
    }

    interface Network{
        companion object {
            const val MOVIES_BASE_URL = "https://api.themoviedb.org/3/"
            const val YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/"
            const val BASE_POSTER_PATH_URL_550 = "http://image.tmdb.org/t/p/w500/"
            const val BASE_POSTER_PATH_URL_185 = "http://image.tmdb.org/t/p/w185/"
            const val BASE_PROFILE_PATH_URL_185 = "http://image.tmdb.org/t/p/w185/"
            const val KEY_MOVIES = ""
            const val KEY_TRAILER = ""
            const val CACHE_SIZE = 10 * 1024 * 1024
            const val MAX_AGE = 60 * 60
            const val MAX_SCALE = 60 * 60 * 24 * 7
        }
    }

    interface Database{
        companion object {
            const val DATABASE_NAME = "LoveMoviesDatabase"
            const val DATABASE_VERSION = 1
            const val LIMIT = 1000
        }
    }

}