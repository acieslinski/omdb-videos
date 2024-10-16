package com.acieslinski.videos.featues.videos.list.viewmodels.models

data class VideosUiState(
    val failure: VideosFailureUiState? = null,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = true,
    val searchQuery: String = "",
    val selectedVideoPosition: Int = 0,
    val videos: List<VideoUiModel> = emptyList()
) {
    fun dismissedFailure() = copy(
        failure = failure?.copy(isDismissed = true)
    )
    fun failure(type: VideosFailureType) = copy(
        failure = VideosFailureUiState(
            type = type
        ),
        isEmpty = true,
        isLoading = false,
        videos = emptyList(),
        selectedVideoPosition = 0
    )

    fun success(videos: List<VideoUiModel>) = copy(
        failure = null,
        isLoading = false,
        isEmpty = videos.isEmpty(),
        videos = videos,
        selectedVideoPosition = 0
    )

    companion object  {
        val DEFAULT = VideosUiState()
    }
}