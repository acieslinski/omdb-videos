package com.acieslinski.videos.featues.videos.list.ui

import com.acieslinski.videos.domain.videos.search.models.Video
import com.acieslinski.videos.domain.videos.search.models.VideosResult

@Suppress("MayBeConstant")
object VideosFragmentTestData {
    val testQuery = "sample video title"
    val successVideo0Title = "sample video title 1"
    val successVideo0Year = "2024"
    val successVideo0 = Video(
        videoId = "test-id-0",
        posterUrl = "test",
        title = "sample video title 1",
        year = 2024,
    )
    val successVideo1Title = "sample video title 2"
    val successVideo1Year = "2019"
    val successVideo1 = Video(
        videoId = "test-id-1",
        posterUrl = "test",
        title = "sample video title 2",
        year = 2019,
    )
    val successVideosResult = VideosResult.Success(
        videos = listOf(successVideo0, successVideo1)
    )
    val networkFailureVideosResult = VideosResult.NetworkFailure
}