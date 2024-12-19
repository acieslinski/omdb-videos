package com.acieslinski.videos.domain.videos.details

import com.acieslinski.videos.domain.videos.details.models.VideoDetails
import com.acieslinski.videos.domain.videos.selection.models.VideoSelection

object GetVideoDetailsSelectionUseCaseTestData {
    val selectedVideoSelection = VideoSelection.Selected(
        videoId = "test-id"
    )
    val videoDetails = VideoDetails(
        videoId = "test-id",
        title = "test-id",
        plotSummary = "summary-id",
        releaseYear = "release-year",
    )
}