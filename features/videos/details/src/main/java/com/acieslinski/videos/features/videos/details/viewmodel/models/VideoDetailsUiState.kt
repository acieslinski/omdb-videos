package com.acieslinski.videos.features.videos.details.viewmodel.models

sealed class VideoDetailsUiState {
    data object Empty : VideoDetailsUiState()
    data object Loading : VideoDetailsUiState()
    data class Success(val videoDetailsUiModel: VideoDetailsUiModel) : VideoDetailsUiState()
    data class Error(val message: String) : VideoDetailsUiState()
    data object NotFound : VideoDetailsUiState() // Represents a 404 result specifically
}