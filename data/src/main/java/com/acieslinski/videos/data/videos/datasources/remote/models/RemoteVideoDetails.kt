package com.acieslinski.videos.data.videos.datasources.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteVideoDetails(
    @SerialName("imdbID")
    val videoId: String,
    @SerialName("Plot")
    val plot: String,
    @SerialName("Title")
    val title: String,
    @SerialName("Released")
    val releaseYear: String,
)