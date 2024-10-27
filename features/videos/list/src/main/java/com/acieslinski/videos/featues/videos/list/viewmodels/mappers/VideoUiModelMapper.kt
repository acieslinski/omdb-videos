package com.acieslinski.videos.featues.videos.list.viewmodels.mappers

import com.acieslinski.videos.domain.videos.search.models.Video
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideoUiModel
import javax.inject.Inject

class VideoUiModelMapper @Inject constructor() {
    fun mapToUi(video: Video) = with(video) {
        VideoUiModel(
            id = videoId,
            posterUrl = posterUrl,
            title = title,
            year = year.toString()
        )
    }
}