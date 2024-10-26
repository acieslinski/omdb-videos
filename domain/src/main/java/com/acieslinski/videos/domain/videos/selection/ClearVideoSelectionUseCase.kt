package com.acieslinski.videos.domain.videos.selection

import javax.inject.Inject

class ClearVideoSelectionUseCase @Inject constructor(
    private val videoSelector: VideoSelector
) {
    operator fun invoke() = videoSelector.clearVideoSelection()
}