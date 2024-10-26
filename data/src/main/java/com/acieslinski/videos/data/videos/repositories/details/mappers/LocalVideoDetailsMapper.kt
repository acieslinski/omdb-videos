package com.acieslinski.videos.data.videos.repositories.details.mappers

import com.acieslinski.videos.data.videos.datasources.local.models.LocalVideoDetails
import com.acieslinski.videos.domain.videos.details.models.VideoDetails
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import javax.inject.Inject

class LocalVideoDetailsMapper @Inject constructor() {
    fun mapToDomain(localVideoDetails: LocalVideoDetails?): VideoDetailsResult =
        localVideoDetails?.let { it ->
            VideoDetails(
                videoId = it.videoId,
                title = it.title,
                plotSummary = it.plotSummary,
                releaseYear = it.releaseYear,
            )
                .let {
                    VideoDetailsResult.Success(it)
                }
        } ?: VideoDetailsResult.NotFoundFailure
}