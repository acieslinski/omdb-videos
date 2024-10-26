package com.acieslinski.videos.domain.videos.details

import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import com.acieslinski.videos.domain.videos.selection.VideoSelector
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoDetailsUseCase @Inject constructor(
    private val videoDetailsProvider: VideoDetailsProvider
) {
    operator fun invoke(videoId: String): Flow<VideoDetailsResult> =
        videoDetailsProvider.getVideoDetails(videoId)
}