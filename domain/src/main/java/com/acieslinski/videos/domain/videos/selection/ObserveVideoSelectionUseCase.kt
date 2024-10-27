package com.acieslinski.videos.domain.videos.selection

import com.acieslinski.videos.domain.videos.selection.models.VideoSelection
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveVideoSelectionUseCase @Inject constructor(
    private val videoSelector: VideoSelector
){
    operator fun invoke(): Flow<VideoSelection> = videoSelector.observeVideoSelection()
}