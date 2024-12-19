package com.acieslinski.videos.featues.videos.list.viewmodels

import com.acieslinski.videos.domain.videos.search.models.Video
import com.acieslinski.videos.domain.videos.search.models.VideosResult
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideoUiModel
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosFailureType
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosFailureUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosUiState

object VideosViewModelTestData {
    val initialVideosUiState = VideosUiState(
        failure = null,
        isLoading = false,
        isEmpty = true,
        searchQuery = "",
        selectedVideoPosition = 0,
        videos = emptyList()
    )
    val emptySuccess = VideosResult.Success(emptyList())
    fun loadingUiState(searchQuery: String) = VideosUiState(
        failure = null,
        isLoading = true,
        isEmpty = true,
        searchQuery = searchQuery,
        selectedVideoPosition = 0,
        videos = emptyList()
    )

    fun singleVideoSuccess(searchQuery: String) = VideosResult.Success(
        listOf(
            Video(videoId = "test", posterUrl = "test", title = searchQuery, year = 2024)
        )
    )

    fun singleVideoUiState(searchQuery: String) = VideosUiState(
        failure = null,
        isLoading = false,
        isEmpty = false,
        searchQuery = searchQuery,
        selectedVideoPosition = 0,
        videos = listOf(
            VideoUiModel(id = "test", posterUrl = "test", title = searchQuery, year = "2024")
        )
    )

    fun multipleVideosSuccess(searchQuery: String) = VideosResult.Success(
        listOf(
            Video(videoId = "test", posterUrl = "test", title = searchQuery, year = 2024),
            Video(videoId = "expected-id", posterUrl = "test", title = searchQuery, year = 2019),
        )
    )

    fun multipleVideosUiState(searchQuery: String, position: Int) = VideosUiState(
        failure = null,
        isLoading = false,
        isEmpty = false,
        searchQuery = searchQuery,
        selectedVideoPosition = position,
        videos = listOf(
            VideoUiModel(id = "test", posterUrl = "test", title = searchQuery, year = "2024"),
            VideoUiModel(
                id = "expected-id",
                posterUrl = "test",
                title = searchQuery,
                year = "2019"
            ),
        )
    )

    fun failureVideosUiState(searchQuery: String, videosUiStateFailureType: VideosFailureType) =
        VideosUiState(
            failure = VideosFailureUiState(
                type = videosUiStateFailureType,
                isDismissed = false,
            ),
            isLoading = false,
            isEmpty = true,
            searchQuery = searchQuery,
            selectedVideoPosition = 0,
            videos = emptyList()
        )

    fun failureDismissedVideosUiState(
        searchQuery: String,
        videosUiStateFailureType: VideosFailureType
    ) =
        VideosUiState(
            failure = VideosFailureUiState(
                type = videosUiStateFailureType,
                isDismissed = true,
            ),
            isLoading = false,
            isEmpty = true,
            searchQuery = searchQuery,
            selectedVideoPosition = 0,
            videos = emptyList()
        )

    fun selectionVideosUiState(position: Int) = VideosUiState(
        failure = null,
        isLoading = false,
        isEmpty = true,
        searchQuery = "",
        selectedVideoPosition = position,
        videos = emptyList()
    )
}