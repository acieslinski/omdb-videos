package com.acieslinski.videos.features.videos.details.viewmodel.mappers

import com.acieslinski.videos.domain.videos.details.models.VideoDetails
import com.acieslinski.videos.features.videos.details.viewmodel.models.VideoDetailsUiModel
import javax.inject.Inject

class VideoDetailsMapper @Inject constructor() {
    fun mapToUi(videoDetails: VideoDetails) = with(videoDetails) {
        VideoDetailsUiModel(
            title = title,
            plotSummary = plotSummary,
            releaseYear = releaseYear,
        )
    }
}