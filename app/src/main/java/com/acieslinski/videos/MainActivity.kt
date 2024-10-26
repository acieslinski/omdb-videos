package com.acieslinski.videos

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.acieslinski.videos.databinding.ActivityMainBinding
import com.acieslinski.videos.featues.videos.list.ui.VideosFragment
import com.acieslinski.videos.features.videos.details.ui.VideoDetailsScreen
import com.acieslinski.videos.ui.theme.VideosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val videoFragmentNavigator = VideoFragmentNavigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VideosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AndroidViewBinding(ActivityMainBinding::inflate) {
                        root.findViewById<ComposeView?>(R.id.composeView)?.setContent {
                            VideoDetailsScreen()
                        }
                    }
                }
            }
        }

        videoFragmentNavigator.setUp()
    }

    override fun onDestroy() {
        videoFragmentNavigator.release()
        super.onDestroy()
    }

    private inner class VideoFragmentNavigator : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentStarted(fm: FragmentManager, fragment: Fragment) {
            if (fragment is VideosFragment) {
                setUpNavigation(fragment)
            }
        }

        private fun setUpNavigation(videosFragment: VideosFragment) {
            videosFragment.apply {
                onVideoItemClickListener = { _ ->
                    val isPortrait =
                        resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
                    if (isPortrait) {
                        val intent = Intent(context, VideoDetailsActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }

        fun setUp() = supportFragmentManager.registerFragmentLifecycleCallbacks(
            this, false)

        fun release() = supportFragmentManager.unregisterFragmentLifecycleCallbacks(this)
    }
}
