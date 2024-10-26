package com.acieslinski.videos.data.videos.repositories.details.mappers

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideoDetails
import com.acieslinski.videos.domain.videos.details.models.VideoDetails
import com.acieslinski.videos.data.videos.datasources.remote.models.RemoteVideoDetails
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import javax.inject.Inject

class RemoteVideoDetailsMapper @Inject constructor() {
    fun mapToDomain(remoteVideoDetails: RemoteVideoDetails) = with(remoteVideoDetails) {
        VideoDetails(
            videoId = videoId,
            title = title,
            plotSummary = plot,
            releaseYear = releaseYear,
        ).let {
            VideoDetailsResult.Success(it)
        }
    }

    fun mapToLocal(remoteVideoDetails: RemoteVideoDetails) = with(remoteVideoDetails) {
        LocalVideoDetails(
            videoId = videoId,
            title = title,
            plotSummary = plot,
            releaseYear = releaseYear
        )
    }
}