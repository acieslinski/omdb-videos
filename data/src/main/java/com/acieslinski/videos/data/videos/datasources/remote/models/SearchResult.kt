package com.acieslinski.videos.data.videos.datasources.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val Search: List<RemoteVideo>,
    val totalResults: String,
    val Response: String
)