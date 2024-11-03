package com.acieslinski.videos.domain.videos.details.models

sealed class VideoDetailsSelectionResult {
    data object UnSelected : VideoDetailsSelectionResult()

    data object Loading : VideoDetailsSelectionResult()

    data class Selected(
        val videoDetailsResult: VideoDetailsResult
    ) : VideoDetailsSelectionResult()
}