package com.acieslinski.videos.featues.videos.list.ui

import android.content.pm.ActivityInfo
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onIdle
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.acieslinski.videos.videos.R
import org.hamcrest.CoreMatchers.equalTo

class VideosFragmentRobot {

    fun enterSearchQuery(query: String): VideosFragmentRobot {
        onView(withId(R.id.searchEditText))
            .perform(typeText(query), closeSoftKeyboard())
        return this
    }

    fun clickSearchButton(): VideosFragmentRobot {
        onView(withId(R.id.searchButton)).perform(click())
        return this
    }

    fun clickDismissAlertButton(): VideosFragmentRobot {
        onView(withText("OK")).perform(click())
        return this
    }

    // could be fragile on specific, small screens
    fun clickOutsideAlertDialog(): VideosFragmentRobot {
        val uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        if (!uiDevice.click(100, 100)) error("Not able to click! Check the screen size")
        return this
    }

    fun verifySearchQuery(query: String): VideosFragmentRobot {
        onView(withId(R.id.searchEditText)).check(matches(withText(query)))
        return this
    }

    fun scrollDown(): VideosFragmentRobot {
        onView(withId(R.id.videosRecyclerView))
            .perform(ViewActions.swipeUp())
        return this
    }

    fun verifyVideoAtPosition(position: Int, title: String, year: String): VideosFragmentRobot {
        onView(withId(R.id.videosRecyclerView))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
            .check(matches(hasDescendant(withText(title))))
            .check(matches(hasDescendant(withText(year))))
        return this
    }

    fun verifyRecyclerViewIsDisplayed(): VideosFragmentRobot {
        onView(withId(R.id.videosRecyclerView)).check(matches(isDisplayed()))
        return this
    }

    fun verifyRecyclerViewIsEmpty(): VideosFragmentRobot {
        onView(withId(R.id.videosRecyclerView)).check { view, _ ->
            val recyclerView = view as RecyclerView
            val itemCount = recyclerView.adapter?.itemCount ?: 0
            assertThat("RecyclerView is not empty", itemCount, equalTo(0))
        }
        return this
    }

    fun verifyNetworkFailureAlertIsDisplayed(): VideosFragmentRobot {
        onView(withText("Example Alert Dialog")).check(matches(isDisplayed()))
        onView(withText(R.string.videos_network_failure)).check(matches(isDisplayed()))
        return this
    }

    fun verifyNetworkFailureAlertIsNotDisplayed(): VideosFragmentRobot {
        onView(withText("Example Alert Dialog")).check(doesNotExist())
        return this
    }

    fun rotatePhone(activity: FragmentActivity): VideosFragmentRobot {
        val newOrientation = if (activity.resources.configuration.orientation ==
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        activity.requestedOrientation = newOrientation

        // Wait for the activity to stabilize after rotation
        onIdle()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        return this
    }
}
