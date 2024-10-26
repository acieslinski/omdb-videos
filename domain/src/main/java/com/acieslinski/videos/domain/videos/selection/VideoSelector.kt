package com.acieslinski.videos.domain.videos.selection

import com.acieslinski.videos.domain.videos.selection.models.VideoSelection
import kotlinx.coroutines.flow.Flow

interface VideoSelector {
    fun selectVideo(videoId: String)

    fun clearVideoSelection()

    fun observeVideoSelection(): Flow<VideoSelection>
}