package com.acieslinski.videos.domain.videos.search

import com.acieslinski.videos.domain.videos.search.models.VideosResult

interface VideoSearcher {
    suspend fun getVideos(searchQuery: String): VideosResult
}