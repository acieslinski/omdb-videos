package com.acieslinski.videos.features.videos.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acieslinski.videos.domain.videos.details.GetVideoDetailsUseCase
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
    observeVideoSelectionUseCase: ObserveVideoSelectionUseCase,
    private val getVideoDetailsUseCase: GetVideoDetailsUseCase,
    private val videoDetailsResultMapper: VideoDetailsResultMapper
) : ViewModel() {
    @OptIn(ExperimentalCoroutinesApi::class)
    val videoDetailsUiState: StateFlow<VideoDetailsUiState> = observeVideoSelectionUseCase()
        .transformLatest {
            when (it) {
                is VideoSelection.Selected -> {
                    getVideoDetailsUseCase(it.videoId)
                        .onStart { emit(VideoDetailsUiState.Loading) }
                        .map { videoDetailsResultMapper.mapToUiState(it) }
                        .onEach { emit(it) }
                        .collect(this)
                }
                else -> emit(VideoDetailsUiState.Empty)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, VideoDetailsUiState.Empty)
}