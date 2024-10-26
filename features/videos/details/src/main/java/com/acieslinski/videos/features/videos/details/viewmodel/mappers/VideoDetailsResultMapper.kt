package com.acieslinski.videos.features.videos.details.viewmodel.mappers

import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import com.acieslinski.videos.features.videos.details.viewmodel.models.VideoDetailsUiState
import javax.inject.Inject

class VideoDetailsResultMapper @Inject constructor(
    private val videoDetailsMapper: VideoDetailsMapper
) {
    fun mapToUiState(videoDetailsResult: VideoDetailsResult) = when (videoDetailsResult) {
        is VideoDetailsResult.Success -> videoDetailsMapper.mapToUi(videoDetailsResult.videoDetails)
            .let {
                VideoDetailsUiState.Success(it)
            }
        is VideoDetailsResult.NotFoundFailure -> VideoDetailsUiState.NotFound
        is VideoDetailsResult.Failure -> VideoDetailsUiState.Error("TODO")
    }
}