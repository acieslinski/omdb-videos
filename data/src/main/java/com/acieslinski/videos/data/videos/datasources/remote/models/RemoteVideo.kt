package com.acieslinski.videos.data.videos.datasources.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteVideo(
    @SerialName("imdbID")
    val videoId: String,
    @SerialName("Poster")
    val posterUrl: String,
    @SerialName("Title")
    val title: String,
    @SerialName("Year")
    val year: String,
)