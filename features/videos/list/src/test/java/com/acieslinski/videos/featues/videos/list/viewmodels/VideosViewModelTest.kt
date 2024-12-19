package com.acieslinski.videos.featues.videos.list.viewmodels

import com.acieslinski.videos.domain.videos.search.SearchVideosUseCase
import com.acieslinski.videos.domain.videos.search.models.VideosResult
import com.acieslinski.videos.domain.videos.selection.ClearVideoSelectionUseCase
import com.acieslinski.videos.domain.videos.selection.SelectVideoDetailsUseCase
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.emptySuccess
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.failureDismissedVideosUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.failureVideosUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.initialVideosUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.loadingUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.multipleVideosSuccess
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.multipleVideosUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.selectionVideosUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.singleVideoSuccess
import com.acieslinski.videos.featues.videos.list.viewmodels.VideosViewModelTestData.singleVideoUiState
import com.acieslinski.videos.featues.videos.list.viewmodels.mappers.VideoUiModelMapper
import com.acieslinski.videos.featues.videos.list.viewmodels.models.VideosFailureType
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class VideosViewModelTest {
    private val searchVideosUseCaseMock = mockk<SearchVideosUseCase>().also {
        coEvery { it.invoke(any()) } returns VideosResult.Success(videos = emptyList())
    }
    private val selectVideoDetailsUseCaseMock = mockk<SelectVideoDetailsUseCase>().also {
        every { it.invoke(any()) } returns Unit
    }
    private val clearVideoSelectionUseCaseMock = mockk<ClearVideoSelectionUseCase>().also {
        every { it.invoke() } returns Unit
    }

    @get:Rule
    val mainDispatcherRule = object : TestWatcher() {
        override fun starting(description: Description) {
            Dispatchers.setMain(UnconfinedTestDispatcher())
        }

        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `should emit correct initial UI state`() = runTest {
        // given
        val subject = createSubject()

        // when
        val uiState = subject.videosUiState.first()

        // then
        assertThat(uiState).isEqualTo(initialVideosUiState)
    }

    @Test
    fun `should emit loading UI state on long search action`() = runTest {
        // given
        val searchQuery = "test"
        val subject = createSubject()
        coEvery { searchVideosUseCaseMock(searchQuery) } coAnswers {
            delay(Long.MAX_VALUE)
            emptySuccess
        }

        // when
        subject.onSearchAction(searchQuery)
        val uiStates = subject.videosUiState.first()

        // then
        assertThat(uiStates).isEqualTo(loadingUiState(searchQuery))
    }

    @Test
    fun `should emit correct UI state on success search action`() = runTest {
        // given
        val searchQuery = "test"
        val subject = createSubject()
        coEvery { searchVideosUseCaseMock(searchQuery) } returns singleVideoSuccess(searchQuery)

        // when
        subject.onSearchAction(searchQuery)
        val uiStates = subject.videosUiState.first()

        // then
        assertThat(uiStates).isEqualTo(singleVideoUiState(searchQuery))
    }

    @Test
    fun `should emit correct UI state on selection action`() = runTest {
        // given
        val position = 1
        val searchQuery = "test"
        val subject = createSubject()
        coEvery { searchVideosUseCaseMock(searchQuery) } returns multipleVideosSuccess(searchQuery)
        subject.onSearchAction(searchQuery)

        // when
        subject.onSelectionAction(position)
        val uiState = subject.videosUiState.first()

        // then
        with(multipleVideosUiState(searchQuery, position)) {
            assertThat(uiState).isEqualTo(this)
            verify { selectVideoDetailsUseCaseMock(videos[position].id) }
        }
    }

    @Test
    fun `should emit correct UI state on search action when server failure`() = runTest {
        `should emit correct UI state on search action when failure`(
            videosSearchResultFailure = VideosResult.ServerFailure,
            videosUiStateFailureType = VideosFailureType.ServerFailure,
        )
    }

    @Test
    fun `should emit correct UI state on search action when network failure`() = runTest {
        `should emit correct UI state on search action when failure`(
            videosSearchResultFailure = VideosResult.NetworkFailure,
            videosUiStateFailureType = VideosFailureType.NetworkFailure,
        )
    }

    @Test
    fun `should emit correct UI state on search action when unknown failure`() = runTest {
        `should emit correct UI state on search action when failure`(
            videosSearchResultFailure = VideosResult.UnknownFailure("any message"),
            videosUiStateFailureType = VideosFailureType.UnknownFailure,
        )
    }

    private fun `should emit correct UI state on search action when failure`(
        videosSearchResultFailure: VideosResult.Failure,
        videosUiStateFailureType: VideosFailureType
    ) = runTest {
        // given
        val searchQuery = "test"
        val subject = createSubject()
        coEvery { searchVideosUseCaseMock(searchQuery) } returns videosSearchResultFailure

        // when
        subject.onSearchAction(searchQuery)
        val uiState = subject.videosUiState.first()

        // then
        assertThat(uiState).isEqualTo(failureVideosUiState(searchQuery, videosUiStateFailureType))
    }

    @Test
    fun `should emit correct UI state on dismiss alert when network failure`() = runTest {
        // given
        val searchQuery = "test"
        val subject = createSubject()
        coEvery { searchVideosUseCaseMock(searchQuery) } returns VideosResult.NetworkFailure
        subject.onSearchAction(searchQuery)

        // when
        subject.onDismissAlert()
        val uiState = subject.videosUiState.first()

        // then
        assertThat(uiState).isEqualTo(
            failureDismissedVideosUiState(searchQuery, VideosFailureType.NetworkFailure)
        )
    }

    @Test
    fun `should emit correct UI state on dismiss alert when no failure`() = runTest {
        // given
        val subject = createSubject()

        // when
        subject.onDismissAlert()
        val uiState = subject.videosUiState.first()

        // then
        assertThat(uiState).isEqualTo(initialVideosUiState)
    }

    @Test
    fun `should emit correct UI state on selection action when no videos`() = runTest {
        // given
        val subject = createSubject()

        // when
        subject.onSelectionAction(Int.MAX_VALUE)
        val uiState = subject.videosUiState.first()

        // then
        assertThat(uiState).isEqualTo(selectionVideosUiState(Int.MAX_VALUE))
        verify(exactly = 0) { selectVideoDetailsUseCaseMock(any()) }
    }

    @Test
    fun `should clear video selection on clear view model`() = runTest {
        // given
        val subject = createSubject()

        // when
        subject.onCleared()

        // then
        verify { clearVideoSelectionUseCaseMock() }
    }

    private fun createSubject() = VideosViewModel(
        searchVideosUseCase = searchVideosUseCaseMock,
        selectVideoDetailsUseCase = selectVideoDetailsUseCaseMock,
        clearVideoSelectionUseCase = clearVideoSelectionUseCaseMock,
        videoUiModelMapper = VideoUiModelMapper()
    )
}