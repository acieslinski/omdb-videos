package com.acieslinski.videos.data.videos.repositories.base.mappers

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideo
import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideo
import com.acieslinski.videos.domain.videos.search.models.Video
import com.acieslinski.videos.domain.videos.search.models.VideosResult
import javax.inject.Inject

class RemoteVideoMapper @Inject constructor() {
    fun mapToLocal(remoteVideo: RemoteVideo) = with(remoteVideo) {
        LocalVideo(
            videoId = videoId,
            posterUrl = posterUrl,
            title = title,
            year = year.extractYear()
        )
    }

    private fun mapToDomain(remoteVideo: RemoteVideo) = with(remoteVideo) {
        Video(
            videoId = videoId,
            posterUrl = posterUrl,
            title = title,
            year = year.extractYear()
        )
    }

    private fun String.extractYear(): Int {
        val yearPattern = """(\d{4})""".toRegex()
        return yearPattern.find(this)?.value?.toIntOrNull() ?: 0
    }

    fun mapToDomain(remoteVideos: List<RemoteVideo>): VideosResult =
        remoteVideos
            .map { mapToDomain(it) }
            .let { VideosResult.Success(it) }
}