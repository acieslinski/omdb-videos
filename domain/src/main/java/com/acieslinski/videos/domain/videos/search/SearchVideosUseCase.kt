package com.acieslinski.videos.domain.videos.search

import com.acieslinski.videos.domain.videos.search.models.VideosResult
import javax.inject.Inject

class SearchVideosUseCase @Inject constructor(
    private val videoSearcher: VideoSearcher
) {
    suspend operator fun invoke(searchQuery: String): VideosResult =
        videoSearcher.getVideos(searchQuery)
}

