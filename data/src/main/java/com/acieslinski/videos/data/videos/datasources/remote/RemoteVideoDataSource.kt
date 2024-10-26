package com.acieslinski.videos.data.videos.datasources.remote

import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideo
import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideoDetails

interface RemoteVideoDataSource {
    suspend fun searchVideos(searchQuery: String): List<RemoteVideo>

    suspend fun getVideoDetails(videoId: String): RemoteVideoDetails
}