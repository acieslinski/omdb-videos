package com.acieslinski.videos.features.videos.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acieslinski.videos.domain.videos.details.GetVideoDetailsSelectionUseCase
import com.acieslinski.videos.domain.videos.details.GetVideoDetailsUseCase
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsSelectionResult
import com.acieslinski.videos.domain.videos.selection.ObserveVideoSelectionUseCase
import com.acieslinski.videos.domain.videos.selection.models.VideoSelection
import com.acieslinski.videos.features.videos.details.viewmodel.mappers.VideoDetailsResultMapper
import com.acieslinski.videos.features.videos.details.viewmodel.models.VideoDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    getVideoDetailsSelectionUseCase: GetVideoDetailsSelectionUseCase,
    private val videoDetailsResultMapper: VideoDetailsResultMapper
) : ViewModel() {
    val videoDetailsUiState: StateFlow<VideoDetailsUiState> = getVideoDetailsSelectionUseCase()
        .map {
            when (it) {
                is VideoDetailsSelectionResult.Selected -> videoDetailsResultMapper.mapToUiState(it.videoDetailsResult)
                is VideoDetailsSelectionResult.Loading -> VideoDetailsUiState.Loading
                else -> VideoDetailsUiState.Empty
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, VideoDetailsUiState.Empty)
}