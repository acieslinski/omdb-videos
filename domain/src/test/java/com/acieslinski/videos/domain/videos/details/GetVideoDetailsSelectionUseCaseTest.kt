package com.acieslinski.videos.domain.videos.details

import com.acieslinski.videos.domain.videos.details.GetVideoDetailsSelectionUseCaseTestData.selectedVideoSelection
import com.acieslinski.videos.domain.videos.details.GetVideoDetailsSelectionUseCaseTestData.videoDetails
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsResult
import com.acieslinski.videos.domain.videos.details.models.VideoDetailsSelectionResult
import com.acieslinski.videos.domain.videos.selection.ObserveVideoSelectionUseCase
import com.acieslinski.videos.domain.videos.selection.models.VideoSelection
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test


class GetVideoDetailsSelectionUseCaseTest {
    private val getVideoDetailsUseCaseMock = mockk<GetVideoDetailsUseCase>().also {
        every { it.invoke(any()) } returns emptyFlow()
    }
    private val observeVideoSelectionUseCaseMock = mockk<ObserveVideoSelectionUseCase>().also {
        every { it.invoke() } returns flowOf(VideoSelection.Unselected)
    }

    @Test
    fun `should emit unselected state`() = runTest {
        // given
        val subject = createSubject()

        // when
        val videoDetailsSelectionResult = subject.invoke().last()

        // then
        assertThat(videoDetailsSelectionResult).isEqualTo(VideoDetailsSelectionResult.UnSelected)
    }

    @Test
    fun `should emit selected loading state`() = runTest {
        // given
        every { observeVideoSelectionUseCaseMock() } returns flowOf(selectedVideoSelection)
        val subject = createSubject()

        // when
        val videoDetailsSelectionResult = subject.invoke().last()

        // then
        assertThat(videoDetailsSelectionResult).isEqualTo(VideoDetailsSelectionResult.Loading)
    }

    @Test
    fun `should emit selected but not found state`() {
        `should emit selected but failure state`(
            videoDetailsFailureResult = VideoDetailsResult.NotFoundFailure,
        )
    }

    @Test
    fun `should emit selected but network failure state`() {
        `should emit selected but failure state`(
            videoDetailsFailureResult = VideoDetailsResult.NetworkFailure,
        )
    }

    @Test
    fun `should emit selected but unknown failure state`() {
        `should emit selected but failure state`(
            videoDetailsFailureResult = VideoDetailsResult.UnknownFailure("test"),
        )
    }

    private fun `should emit selected but failure state`(
        videoDetailsFailureResult: VideoDetailsResult.Failure,
    ) = runTest {
        // given
        every { observeVideoSelectionUseCaseMock() } returns flowOf(selectedVideoSelection)
        every { getVideoDetailsUseCaseMock(selectedVideoSelection.videoId) } returns flowOf(
            videoDetailsFailureResult
        )
        val subject = createSubject()

        // when
        val videoDetailsSelectionResult = subject.invoke().last()

        // then
        assertThat(videoDetailsSelectionResult).isEqualTo(
            VideoDetailsSelectionResult.Selected(
                videoDetailsResult = videoDetailsFailureResult
            )
        )
    }

    @Test
    fun `should emit selected success state`() = runTest {
        // given
        every { observeVideoSelectionUseCaseMock() } returns flowOf(selectedVideoSelection)
        every { getVideoDetailsUseCaseMock(selectedVideoSelection.videoId) } returns flowOf(
            VideoDetailsResult.Success(
                videoDetails = videoDetails
            )
        )
        val subject = createSubject()

        // when
        val videoDetailsSelectionResult = subject.invoke().last()

        // then
        assertThat(videoDetailsSelectionResult).isEqualTo(
            VideoDetailsSelectionResult.Selected(
                videoDetailsResult = VideoDetailsResult.Success(
                    videoDetails = videoDetails
                )
            )
        )
    }

    private fun createSubject() = GetVideoDetailsSelectionUseCase(
        getVideoDetailsUseCase = getVideoDetailsUseCaseMock,
        observeVideoSelectionUseCase = observeVideoSelectionUseCaseMock,
    )
}