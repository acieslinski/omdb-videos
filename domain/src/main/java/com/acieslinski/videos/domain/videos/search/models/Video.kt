package com.acieslinski.videos.domain.videos.search.models

data class Video(
    val videoId: String,
    val posterUrl: String,
    val title: String,
    val year: Int,
)