package com.acieslinski.videos.data.videos.repositories.selection

import com.acieslinski.videos.domain.videos.selection.VideoSelector
import com.acieslinski.videos.domain.videos.selection.models.VideoSelection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoSelectionRepository @Inject constructor(): VideoSelector {
    private val _selectedVideoId = MutableStateFlow<VideoSelection>(VideoSelection.Unselected)

    override fun selectVideo(videoId: String) {
        _selectedVideoId.value = VideoSelection.Selected(videoId)
    }

    override fun clearVideoSelection() {
        _selectedVideoId.value = VideoSelection.Unselected
    }

    override fun observeVideoSelection(): Flow<VideoSelection> = _selectedVideoId
}