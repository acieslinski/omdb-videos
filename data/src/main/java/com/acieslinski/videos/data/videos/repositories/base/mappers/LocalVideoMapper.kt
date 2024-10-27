package com.acieslinski.videos.data.videos.repositories.base.mappers

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo
import com.acieslinski.videos.domain.videos.search.models.Video
import com.acieslinski.videos.domain.videos.search.models.VideosResult
import javax.inject.Inject

class LocalVideoMapper @Inject constructor() {

    fun mapToDomain(localVideos: List<LocalVideo>): VideosResult =
        localVideos
            .map { mapToDomain(it) }
            .let { VideosResult.Success(it) }

    private fun mapToDomain(localVideo: LocalVideo) = with(localVideo) {
        Video(
            videoId = videoId,
            posterUrl = posterUrl,
            title = title,
            year = year
        )
    }
}