package com.acieslinski.videos.domain.videos.selection.models

sealed class VideoSelection {
    data class Selected(val videoId: String) : VideoSelection()

    data object Unselected : VideoSelection()
}