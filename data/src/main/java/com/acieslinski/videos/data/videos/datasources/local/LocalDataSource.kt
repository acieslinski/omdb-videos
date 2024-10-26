package com.acieslinski.videos.data.videos.datasources.local

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo
import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideoDetails

interface LocalDataSource {
    suspend fun saveVideos(videos: List<LocalVideo>)

    suspend fun searchVideos(searchQuery: String): List<LocalVideo>

    suspend fun insertVideoDetails(localVideoDetails: LocalVideoDetails)

    suspend fun getVideoDetails(videoId: String): LocalVideoDetails?
}