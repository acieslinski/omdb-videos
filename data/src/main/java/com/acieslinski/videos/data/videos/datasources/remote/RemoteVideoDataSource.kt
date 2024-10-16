package com.acieslinski.videos.data.videos.datasources.remote

import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideo

interface RemoteVideoDataSource {
    suspend fun searchVideos(searchQuery: String): List<RemoteVideo>
}