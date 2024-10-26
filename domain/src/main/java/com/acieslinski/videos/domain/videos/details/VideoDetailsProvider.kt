package com.acieslinski.videos.domain.videos.details

import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import kotlinx.coroutines.flow.Flow

interface VideoDetailsProvider {
    fun getVideoDetails(videoId: String): Flow<VideoDetailsResult>
}