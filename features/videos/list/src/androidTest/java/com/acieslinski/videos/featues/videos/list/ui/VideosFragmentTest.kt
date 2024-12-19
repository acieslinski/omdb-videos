package com.acieslinski.videos.featues.videos.list.ui

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.networkFailureVideosResult
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.successVideo0
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.successVideo0Title
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.successVideo0Year
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.successVideo1
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.successVideo1Title
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.successVideo1Year
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.successVideosResult
import com.acieslinski.videos.featues.videos.list.ui.VideosFragmentTestData.testQuery
import com.acieslinski.videos.featues.videos.list.ui.VideosTestModule.videoSearcherMock
import com.acieslinski.videos.featues.videos.list.ui.VideosTestModule.videoSelectorMock
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith

interface FragileTest

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class VideosFragmentTest {
    init {
        coEvery { videoSearcherMock.getVideos(any()) } returns successVideosResult
        every { videoSelectorMock.observeVideoSelection() } returns emptyFlow()
        every { videoSelectorMock.selectVideo(any()) } returns Unit
        every { videoSelectorMock.clearVideoSelection() } returns Unit
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val fragmentRule = FragmentTestRule(
        fragmentClass = VideosFragment::class.java,
    )

    private val robot = VideosFragmentRobot()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testRecyclerViewIsEmpty() {
        robot.verifyRecyclerViewIsDisplayed()
            .verifyRecyclerViewIsEmpty()
    }

    @Test
    fun testRecyclerViewDisplaysVideoItems() {
        robot.verifyRecyclerViewIsDisplayed()
            .enterSearchQuery(testQuery)
            .clickSearchButton()
            .verifyVideoAtPosition(0, successVideo0Title, successVideo0Year)
            .verifyVideoAtPosition(1, successVideo1Title, successVideo1Year)
    }

    @Test
    fun testRecyclerViewSelectionAfterVideosLoaded() {
        robot.verifyRecyclerViewIsDisplayed()
            .enterSearchQuery(testQuery)
            .clickSearchButton()

        verify { videoSelectorMock.selectVideo(successVideo0.videoId) }
    }

    @Test
    fun testRecyclerViewSelectionAfterScroll() {
        robot.verifyRecyclerViewIsDisplayed()
            .enterSearchQuery(testQuery)
            .clickSearchButton()
            .scrollDown()

        verify { videoSelectorMock.selectVideo(successVideo1.videoId) }
    }

    @Test
    fun testSearchAfterRotation() {
        robot.enterSearchQuery(testQuery)
            .verifySearchQuery(testQuery)
            .rotatePhone(fragmentRule.activity)
            .verifySearchQuery(testQuery)

        // TODO the input query should be restored after rotation
    }

    @Test
    fun testUiAfterRotation() {
        robot.enterSearchQuery(testQuery)
            .clickSearchButton()
            .rotatePhone(fragmentRule.activity)
            .verifySearchQuery(testQuery)
            .verifyVideoAtPosition(0, successVideo0Title, successVideo0Year)
    }

    @Test
    fun testAlertDialogDisplaysOnNetworkError() {
        coEvery { videoSearcherMock.getVideos(any()) } returns networkFailureVideosResult

        robot
            .enterSearchQuery(testQuery)
            .clickSearchButton()
            .verifyNetworkFailureAlertIsDisplayed()
    }

    @Test
    fun testAlertDialogDisplaysOnNetworkErrorAfterRotation() {
        coEvery { videoSearcherMock.getVideos(any()) } returns networkFailureVideosResult

        robot
            .enterSearchQuery(testQuery)
            .clickSearchButton()
            .rotatePhone(fragmentRule.activity)
            .verifyNetworkFailureAlertIsDisplayed()
    }

    @Test
    fun testAlertDialogDismissButton() {
        coEvery { videoSearcherMock.getVideos(any()) } returns networkFailureVideosResult

        robot
            .enterSearchQuery(testQuery)
            .clickSearchButton()
            .verifyNetworkFailureAlertIsDisplayed()
            .clickDismissAlertButton()
            .verifyNetworkFailureAlertIsNotDisplayed()
    }

    @Test
    @Category(FragileTest::class)
    fun testAlertDialogDismiss() {
        coEvery { videoSearcherMock.getVideos(any()) } returns networkFailureVideosResult

        robot
            .enterSearchQuery(testQuery)
            .clickSearchButton()
            .verifyNetworkFailureAlertIsDisplayed()
            .clickOutsideAlertDialog()
            .verifyNetworkFailureAlertIsNotDisplayed()
    }
}
