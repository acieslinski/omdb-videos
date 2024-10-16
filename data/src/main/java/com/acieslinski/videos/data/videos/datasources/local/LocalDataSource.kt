package com.acieslinski.videos.data.videos.datasources.local

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo

interface LocalDataSource {
    suspend fun saveVideos(videos: List<LocalVideo>)

    suspend fun searchVideos(searchQuery: String): List<LocalVideo>
}