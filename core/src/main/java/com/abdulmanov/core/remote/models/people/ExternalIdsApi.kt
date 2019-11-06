package com.abdulmanov.core.remote.models.people

data class ExternalIdsApi(
    val facebook_id: String?,
    val freebase_id: String?,
    val freebase_mid: String?,
    val imdb_id: String?,
    val instagram_id: String?,
    val tvrage_id: String?,
    val twitter_id: String?
)