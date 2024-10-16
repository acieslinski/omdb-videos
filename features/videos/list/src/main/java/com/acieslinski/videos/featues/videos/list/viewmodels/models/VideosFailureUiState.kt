package com.acieslinski.videos.featues.videos.list.viewmodels.models

data class VideosFailureUiState(
    val type: VideosFailureType = VideosFailureType.UnknownFailure,
    val isDismissed: Boolean = false,
)