package com.abdulmanov.core.common

interface Constant {

    interface Network{
        companion object {
            const val MOVIES_BASE_URL = "https://api.themoviedb.org/3/"
            const val KEY_MOVIES = "d5a20babadb07a6d9680fb3db1db6faf"
            const val CACHE_SIZE = 10 * 1024 * 1024
            const val MAX_AGE = 60 * 60
            const val MAX_SCALE = 60 * 60 * 24 * 7
        }
    }

    interface Database{
        companion object {
            const val DATABASE_NAME = "MoviesDatabase"
            const val DATABASE_VERSION = 1
            const val LIMIT = 1000
        }
    }

}