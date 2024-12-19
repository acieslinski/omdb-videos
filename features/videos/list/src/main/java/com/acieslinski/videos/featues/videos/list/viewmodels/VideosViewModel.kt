package com.acieslinski.videos.featues.videos.list.viewmodels

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acieslinski.videos.domain.videos.search.SearchVideosUseCase
import com.acieslinski.videos.domain.videos.search.models.VideosResult
import com.acieslinski.videos.domain.videos.selection.ClearVideoSelectionUseCase
import com.acieslinski.videos.domain.videos.selection.SelectVideoDetailsUseCase
import com.acieslinski.videos.featues.videos.list.viewmodels.mappers.VideoUiModelMapper
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosFailureType
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(
    private val searchVideosUseCase: SearchVideosUseCase,
    private val selectVideoDetailsUseCase: SelectVideoDetailsUseCase,
    private val clearVideoSelectionUseCase: ClearVideoSelectionUseCase,
    private val videoUiModelMapper: VideoUiModelMapper
) : ViewModel() {
    private val _videosUiState = MutableStateFlow(VideosUiState.DEFAULT)
    val videosUiState = _videosUiState.asStateFlow()
    private var job: Job? = null

    fun onSearchAction(query: String) {
        _videosUiState.update { it.copy(searchQuery = query) }
        job?.cancel()
        job = viewModelScope.launch {
            _videosUiState.update { it.copy(isLoading = true) }
            searchVideosUseCase(query).also { videosResult ->
                updateUiState(videosResult)
                _videosUiState.value.selectedVideo?.let {
                    selectVideoDetailsUseCase(it.id)
                } ?: clearVideoSelectionUseCase()
            }
        }
    }

    private fun updateUiState(result: VideosResult) = when (result) {
        is VideosResult.Success -> _videosUiState.update {
            it.success(
                result.videos.map { video ->
                    videoUiModelMapper.mapToUi(video)
                }
            )
        }
        is VideosResult.ServerFailure ->  {
            _videosUiState.update { it.failure(VideosFailureType.ServerFailure) }
        }
        is VideosResult.NetworkFailure ->  {
            _videosUiState.update { it.failure(VideosFailureType.NetworkFailure) }
        }
        else -> {
            _videosUiState.update { it.failure(VideosFailureType.UnknownFailure) }
        }
    }

    fun onSelectionAction(position: Int) {
        _videosUiState.update { it.copy(selectedVideoPosition = position) }
        _videosUiState.value.selectedVideo?.let { selectVideoDetailsUseCase(it.id) }
    }

    fun onDismissAlert() {
        _videosUiState.update { it.dismissedFailure() }
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public override fun onCleared() {
        clearVideoSelectionUseCase()
        super.onCleared()
    }
}