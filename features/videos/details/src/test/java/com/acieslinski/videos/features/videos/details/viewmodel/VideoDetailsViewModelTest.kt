package com.acieslinski.videos.features.videos.details.viewmodel

import com.acieslinski.videos.domain.videos.details.GetVideoDetailsSelectionUseCase
import com.acieslinski.videos.domain.videos.details.models.VideoDetails
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsSelectionResult
import com.acieslinski.videos.features.videos.details.viewmodel.mappers.VideoDetailsMapper
import com.acieslinski.videos.features.videos.details.viewmodel.mappers.VideoDetailsResultMapper
import com.acieslinski.videos.features.videos.details.viewmodel.models.VideoDetailsUiModel
import com.acieslinski.videos.features.videos.details.viewmodel.models.VideoDetailsUiState
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class VideoDetailsViewModelTest {
    private val getVideoDetailsSelectionUseCaseMock =
        mockk<GetVideoDetailsSelectionUseCase>().also {
            every { it.invoke() } returns emptyFlow()
        }
    private val videoDetailsMapper = VideoDetailsMapper()
    private val videoDetailsResultMapper = VideoDetailsResultMapper(videoDetailsMapper)

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
        val uiState = subject.videoDetailsUiState.first()

        // then
        assertThat(uiState).isEqualTo(VideoDetailsUiState.Empty)
    }

    @Test
    fun `should emit correct loading UI state`() = runTest {
        // given
        every { getVideoDetailsSelectionUseCaseMock() } returns flowOf(
            VideoDetailsSelectionResult.Loading
        )
        val subject = createSubject()

        // when
        val uiState = subject.videoDetailsUiState.first()

        // then
        assertThat(uiState).isEqualTo(VideoDetailsUiState.Loading)
    }

    @Test
    fun `should emit correct success UI state`() = runTest {
        // given
        every { getVideoDetailsSelectionUseCaseMock() } returns flowOf(
            VideoDetailsSelectionResult.Selected(
                videoDetailsResult = VideoDetailsResult.Success(
                    VideoDetails(
                        videoId = "test-id",
                        title = "title-test",
                        plotSummary = "summary-test",
                        releaseYear = "year-test"
                    )
                )
            )
        )
        val subject = createSubject()

        // when
        val uiState = subject.videoDetailsUiState.first()

        // then
        assertThat(uiState).isEqualTo(VideoDetailsUiState.Success(
            videoDetailsUiModel = VideoDetailsUiModel(
                title = "title-test",
                plotSummary = "summary-test",
                releaseYear = "year-test",
            )
        ))
    }

    @Test
    fun `should emit correct network failure UI state`() = runTest {
        // given
        every { getVideoDetailsSelectionUseCaseMock() } returns flowOf(
            VideoDetailsSelectionResult.Selected(
                videoDetailsResult = VideoDetailsResult.NetworkFailure
            )
        )
        val subject = createSubject()

        // when
        val uiState = subject.videoDetailsUiState.first()

        // then
        assertThat(uiState).isEqualTo(VideoDetailsUiState.Error("TODO"))
        TODO("error message is not provided for an network failure")
    }

    @Test
    fun `should emit correct failure UI state`() = runTest {
        // given
        every { getVideoDetailsSelectionUseCaseMock() } returns flowOf(
            VideoDetailsSelectionResult.Selected(
                videoDetailsResult = VideoDetailsResult.NotFoundFailure
            )
        )
        val subject = createSubject()

        // when
        val uiState = subject.videoDetailsUiState.first()

        // then
        assertThat(uiState).isEqualTo(VideoDetailsUiState.NotFound)
    }

    @Test
    fun `should emit correct unknown failure UI state`() = runTest {
        // given
        every { getVideoDetailsSelectionUseCaseMock() } returns flowOf(
            VideoDetailsSelectionResult.Selected(
                videoDetailsResult = VideoDetailsResult.UnknownFailure("message-test")
            )
        )
        val subject = createSubject()

        // when
        val uiState = subject.videoDetailsUiState.first()

        // then
        assertThat(uiState).isEqualTo(VideoDetailsUiState.Error("TODO"))
        TODO("error message is not provided for an unknown failure")
    }

    @Test
    fun `should emit correct empty UI state`() = runTest {
        // given
        every { getVideoDetailsSelectionUseCaseMock() } returns flowOf(
            VideoDetailsSelectionResult.UnSelected
        )
        val subject = createSubject()

        // when
        val uiState = subject.videoDetailsUiState.first()

        // then
        assertThat(uiState).isEqualTo(VideoDetailsUiState.Empty)
    }

    private fun createSubject() = VideoDetailsViewModel(
        getVideoDetailsSelectionUseCase = getVideoDetailsSelectionUseCaseMock,
        videoDetailsResultMapper = videoDetailsResultMapper,
    )
}