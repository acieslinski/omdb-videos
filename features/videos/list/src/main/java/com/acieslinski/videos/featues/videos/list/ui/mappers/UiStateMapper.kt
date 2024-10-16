package com.acieslinski.videos.featues.videos.list.ui.mappers

import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosFailureType
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosFailureUiState
import com.acieslinski.videos.videos.R

class UiStateMapper {
    fun mapToStringResource(uiState: VideosFailureUiState?): Int? {
        return uiState?.let {
            when (it.type) {
                is VideosFailureType.NetworkFailure -> R.string.videos_network_failure
                else -> R.string.videos_unknown_exception
            }
        }
    }
}