package com.acieslinski.videos.domain.videos.details

import com.acieslinski.videos.domain.videos.details.models.VideoDetailsSelectionResult
import com.acieslinski.videos.domain.videos.selection.ObserveVideoSelectionUseCase
import com.acieslinski.videos.domain.videos.selection.VideoSelector
import com.acieslinski.videos.domain.videos.selection.models.VideoSelection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transformLatest
import javax.inject.Inject

class GetVideoDetailsSelectionUseCase @Inject constructor(
    private val getVideoDetailsUseCase: GetVideoDetailsUseCase,
    private val observeVideoSelectionUseCase: ObserveVideoSelectionUseCase,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<VideoDetailsSelectionResult> = observeVideoSelectionUseCase()
        .transformLatest { videoSelection ->
            when (videoSelection) {
                is VideoSelection.Selected -> {
                    getVideoDetailsUseCase(videoSelection.videoId)
                        .onStart { emit(VideoDetailsSelectionResult.Loading) }
                        .map { (VideoDetailsSelectionResult.Selected(it)) }
                        .collect(this)
                }

                else -> emit(VideoDetailsSelectionResult.UnSelected)
            }
        }
}