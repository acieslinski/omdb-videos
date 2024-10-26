package com.acieslinski.videos.domain.videos.selection

import javax.inject.Inject

class SelectVideoDetailsUseCase @Inject constructor(
    private val videoDetailsSelector: VideoSelector
) {
    operator fun invoke(videoId: String) = videoDetailsSelector.selectVideo(videoId)
}